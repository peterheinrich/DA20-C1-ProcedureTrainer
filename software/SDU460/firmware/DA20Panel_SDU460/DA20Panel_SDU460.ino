#include "RPi_Pico_TimerInterrupt.h"
#include "RPi_Pico_TimerInterrupt.h"


#define LED         19

#define OUT_LATCH   2
#define OUT_CLK     3
#define OUT_D       8

#define IN_LATCH    13
#define IN_CLK      12
#define IN_D        14


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
  
  moveSteppers(0);
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

  digitalWrite(OUT_LATCH,0);
  digitalWrite(OUT_CLK,0);
  digitalWrite(OUT_D,0);
  digitalWrite(IN_LATCH,0);
  digitalWrite(IN_CLK,0);
  digitalWrite(STEP_SBY,1);

  ITimer1.attachInterrupt(TIMER_FREQ_HZ, TimerHandler);

}

// the loop routine runs over and over again forever:
void loop() {
  // wait for the nex 100ms mark
  while (timer < 100) {};
  timer = 0;

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
  delayMicroseconds(1);   
  digitalWrite(IN_LATCH,1);
  delayMicroseconds(1);   
  digitalWrite(IN_LATCH,0);
  for(int i = 0; i < 32; i++) {
    // Clock
    delayMicroseconds(1);   
    digitalWrite(OUT_CLK,1);
    delayMicroseconds(1);   
    digitalWrite(OUT_CLK,0);
    res |= digitalRead(IN_D) << 31-i;
  }
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

