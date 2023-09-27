# DA20-C1-ProcedureTrainer
DA20 Hardware Cockpit Simulator for Procedure Training (non approved simulator)

# Final Training Setup
![alt text](https://github.com/peterheinrich/DA20-C1-ProcedureTrainer/blob/main/assets/IMG_2663.jpeg "Final Training Setup")

# Generic Instrument I/O Card based on Raspberry-PI CM4
![alt text](https://github.com/peterheinrich/DA20-C1-ProcedureTrainer/blob/main/assets/IMG_2553.jpeg "Cockpit CPU Card")
![alt text](https://github.com/peterheinrich/DA20-C1-ProcedureTrainer/blob/main/assets/IMG_2557.jpeg "With Raspberry-PI CM4 installed")



# Running with FgFs (FlightGear Flight Simulator)

/usr/local/fgfs.app/Contents/MacOS/fgfs --fg-scenery=/Users/peter/Development/flightgear/terraGIT --airport=LSMF --enable-auto-coordination --aircraft=DA20-C1 --httpd=8080 --generic=socket,out,10,255.255.255.255,30000,udp,glasspanel-stream --timeofday=noon --altitude=10000 
