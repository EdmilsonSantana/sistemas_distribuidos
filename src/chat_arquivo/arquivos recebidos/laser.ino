int laser = 10;
int shock = 9;
int state;
void setup() {
  
  pinMode(laser, OUTPUT);
  pinMode(shock, INPUT);
  Serial.begin(9600);

}

void loop() {
  state = digitalRead(shock);
  Serial.println(state, DEC);
  if( state == HIGH)
    digitalWrite(laser, LOW);
  else
    digitalWrite(laser, HIGH);

}
