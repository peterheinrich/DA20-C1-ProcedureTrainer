#include "RPi_Pico_TimerInterrupt.h"
#include "RPi_Pico_TimerInterrupt.h"


#define LED         19

#define OUT_LATCH   2
#define OUT_CLK     3
#define OUT_D       8

#define IN_LATCH    13
#define IN_CLK      12
#define IN_D        14

#define IN_D        14
#define CM4_ENAB    17
#define EXT_ENAB    18


volatile unsigned int outputRegister = 0;
volatile unsigned int inputRegister = 0;


#define STP_M1_PHASE_A_BIT (1 << 24)
#define STP_M1_PHASE_B_BIT (1 << 25)
#define STP_M2_PHASE_A_BIT (1 << 26)
#define STP_M2_PHASE_B_BIT (1 << 27)
#define STP_M3_PHASE_A_BIT (1 << 28)
#define STP_M3_PHASE_B_BIT (1 << 29)
#define STP_M4_PHASE_A_BIT (1 << 30)
#define STP_M4_PHASE_B_BIT (1 << 31)

#define STEP_SBY          11
#define STEP_M4_EN_A      9
#define STEP_M4_EN_B      10

RPI_PICO_Timer ITimer1(1);


volatile unsigned long timer = 0;
volatile int state = 0;  
volatile bool flagSendUpdate = false;

bool TimerHandler(struct repeating_timer *t)
{ 
  timer ++;
  
  // moveSteppers(0);
  unsigned int inputRegisterNew = readInputRegister();
  if(inputRegister != inputRegisterNew) {
    inputRegister = inputRegisterNew;
    flagSendUpdate = true;
  }
  //readInputAnalog();
  return true;
}

#define TIMER_FREQ_HZ        500

void setup() {
  pinMode(LED, OUTPUT);
  pinMode(OUT_LATCH, OUTPUT);
  pinMode(OUT_CLK, OUTPUT);
  pinMode(OUT_D, OUTPUT);
  pinMode(STEP_SBY, OUTPUT);

  pinMode(IN_LATCH, OUTPUT);
  pinMode(IN_CLK, OUTPUT);
  pinMode(IN_D, INPUT);


  pinMode(CM4_ENAB, OUTPUT);
  pinMode(EXT_ENAB, OUTPUT);

  digitalWrite(CM4_ENAB,1);
  digitalWrite(EXT_ENAB,1);


  digitalWrite(OUT_LATCH,0);
  digitalWrite(OUT_CLK,0);
  digitalWrite(OUT_D,0);
  digitalWrite(IN_LATCH,0);
  digitalWrite(IN_CLK,0);
  digitalWrite(STEP_SBY,1);

  Serial2.setRX(5);
  Serial2.setTX(4);
  Serial2.begin(9600);

  //Serial2.setRX(5);
  //Serial2.setTX(4);
  //Serial2.begin(9600);

  ITimer1.attachInterrupt(TIMER_FREQ_HZ, TimerHandler);
  Serial2.setTimeout(100);
}

char buffer[64];
// the loop routine runs over and over again forever:
void loop() {
  
  if(flagSendUpdate) {
    sendByteValueHex((inputRegister >> 24) & 0xFF);
    Serial2.print(",");
    sendByteValueHex((inputRegister >> 16) & 0xFF);
    Serial2.print(",");
    sendByteValueHex((inputRegister >> 8) & 0xFF);
    Serial2.print(",");
    sendByteValueHex((inputRegister) & 0xFF);
    Serial2.println();
    flagSendUpdate = false;
  }
  Serial2.readBytesUntil('\n', buffer, 64);
  String line = String(buffer);
  char byteBuffer[8];
  char *end;
  line.substring(0,1).toCharArray(byteBuffer, 8);
  unsigned char c0 = strtol(byteBuffer,&end,16);
  line.substring(3,4).toCharArray(byteBuffer, 8);
  unsigned char c1 = strtol(byteBuffer,&end,16);
  line.substring(6,7).toCharArray(byteBuffer, 8);
  unsigned char c2= strtol(byteBuffer,&end,16);  
  outputRegister = c0 | c1 << 8 | c2 << 16;

  writeOutputRegister();

  
}

void sendByteValueHex(unsigned short val) {
  if(val < 16) Serial2.print("0");
  Serial2.print(val, HEX);
}



void writeOutputRegister() {
  unsigned int v;
  for(int i = 0; i < 32; i++) {
    v = outputRegister >> 31-i;
    digitalWrite(OUT_D,v & 0x01);
    // Clock
    delayMicroseconds(1);   
    digitalWrite(OUT_CLK,1);
    delayMicroseconds(1);   
    digitalWrite(OUT_CLK,0);
    delayMicroseconds(1);   

  }
  // Latch
  delayMicroseconds(1);   
  digitalWrite(OUT_LATCH,1);
  delayMicroseconds(1);   
  digitalWrite(OUT_LATCH,0);
}

unsigned int readInputRegister() {
  unsigned int res = 0;
  // Latch
  digitalWrite(IN_LATCH,1);
  delayMicroseconds(5);   
   
  for(int i = 0; i < 32; i++) {
    // Clock
        res |= digitalRead(IN_D) << 31-i;

    delayMicroseconds(1);   
    digitalWrite(IN_CLK,1);
    delayMicroseconds(1);   
    digitalWrite(IN_CLK,0);
  }

  digitalWrite(IN_LATCH,0);
  return res;
}


volatile char stp1_state = 0;
volatile int stp1_pos = 0;

void moveSteppers(int targetPos) {
  if(targetPos > stp1_pos) {
    moveStepperSingleStep(1);
  }
  if(targetPos < stp1_pos) {
    moveStepperSingleStep(0);
  }
}

void moveStepperSingleStep(char dir) {
  switch(state) {
    case 0:
      outputRegister &= ~STP_M1_PHASE_A_BIT;
      outputRegister |= STP_M1_PHASE_B_BIT;
      dir?stp1_state = 1:stp1_state = 3;
      break;
    case 1:
      outputRegister |= STP_M1_PHASE_A_BIT;
      outputRegister |= STP_M1_PHASE_B_BIT;
      dir?stp1_state = 2:stp1_state = 0;
      break;
    case 2:
      outputRegister |= STP_M1_PHASE_A_BIT;
      outputRegister &= ~STP_M1_PHASE_B_BIT;
      dir?stp1_state = 3:stp1_state = 1;
      break;
    case 3:
      outputRegister &= ~STP_M1_PHASE_A_BIT;
      outputRegister &= ~STP_M1_PHASE_B_BIT;      
      dir?stp1_state = 0:stp1_state = 2;
      break;
    default:
      return;
  }
  dir?stp1_pos++:stp1_pos--;
  writeOutputRegister();

}

