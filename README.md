# DD2480-Lab-1

### Description of the program
The Launch Interceptor Program is a software system designed as part of a hypothetical anti-ballistic missile system. The core function, DECIDE(), evaluates radar tracking data to determine whether an interceptor missile should be launched. This decision is based on multiple Launch Interceptor Conditions (LICs), logical connectors, and decision matrices.


### How to run 
    - mvn clean install
    - mvn test
    - mvn test -Dtest=classname (if want to test a specific test class)
### Statement of contributions
Adam Lihagen:
- LIC 6, 8, 10 and 14
- Documentation (README file)

Filip Hedman:
 - FUV function
 - Declarations class
 - LIC 7,9,11

Love Göransson:
 - PUM function
 - LIC 0,1,2,3,4,5 
 - Maven config 


Robin Widjeback:
- LIC 12, 13
- Decide class

### Functionality

The DECIDE() function processes data points representing radar echoes and determines the launch decision based on the following components:

- Conditions Met Vector (CMV): A boolean vector indicating which of the 15 LICs are met.

- Logical Connector Matrix (LCM): A 15x15 symmetric matrix specifying how LICs should be combined using AND, OR, or NOTUSED logic.

- Preliminary Unlocking Matrix (PUM): A 15x15 matrix derived from the CMV and LCM.

- Preliminary Unlocking Vector (PUV): A vector indicating which LICs are relevant for the final decision.

- Final Unlocking Vector (FUV): A vector determining the final decision for interceptor launch.

### Launch Decision

If and only if all values in the FUV are true, the system outputs YES, indicating an interceptor should be launched. Otherwise, it outputs NO.


### Way of working
Our group is probably in the "In-Use" state. The members of the group are using, and adapting, the way-of-working. To be able to reach the next state, which will be the "In-Place" state, the communication within the group can be improved and also the experience of using git, which we believe will happen naturally while working with this course.