#include <LedControl.h>
#include <Time.h>



#define SCROLL_DELAY 10 //ms
#define LED_INTENSITY 1 //1-15

const unsigned char CH[] = {
  B00000000, B00000000, B00000000, B00000000, B00000000, // space
  B01011111, B00000000, B00000000, B00000000, B00000000, // !
  B00000011, B00000000, B00000011, B00000000, B00000000, // "
  B00010100, B00111110, B00010100, B00111110, B00010100, // #
  B00100100, B01101010, B00101011, B00010010, B00000000, // $
  B01100011, B00010011, B00001000, B01100100, B01100011, // %
  B00110110, B01001001, B01010110, B00100000, B01010000, // &
  B00000011, B00000000, B00000000, B00000000, B00000000, // '
  B00011100, B00100010, B01000001, B00000000, B00000000, // (
  B01000001, B00100010, B00011100, B00000000, B00000000, // )
  B00101000, B00011000, B00001110, B00011000, B00101000, // *
  B00001000, B00001000, B00111110, B00001000, B00001000, // +
  B10110000, B01110000, B00000000, B00000000, B00000000, // ,
  B00001000, B00001000, B00001000, B00001000, B00000000, // -
  B01100000, B01100000, B00000000, B00000000, B00000000, // .
  B01100000, B00011000, B00000110, B00000001, B00000000, // /
  B00111110, B01000001, B01000001, B00111110, B00000000, // 0
  B01000010, B01111111, B01000000, B00000000, B00000000, // 1
  B01100010, B01010001, B01001001, B01000110, B00000000, // 2
  B00100010, B01000001, B01001001, B00110110, B00000000, // 3
  B00011000, B00010100, B00010010, B01111111, B00000000, // 4
  B00100111, B01000101, B01000101, B00111001, B00000000, // 5
  B00111110, B01001001, B01001001, B00110000, B00000000, // 6
  B01100001, B00010001, B00001001, B00000111, B00000000, // 7
  B00110110, B01001001, B01001001, B00110110, B00000000, // 8
  B00000110, B01001001, B01001001, B00111110, B00000000, // 9
  B01010000, B00000000, B00000000, B00000000, B00000000, // :
  B10000000, B01010000, B00000000, B00000000, B00000000, // ;
  B00010000, B00101000, B01000100, B00000000, B00000000, // <
  B00010100, B00010100, B00010100, B00000000, B00000000, // =
  B01000100, B00101000, B00010000, B00000000, B00000000, // >
  B00000010, B01011001, B00001001, B00000110, B00000000, // ?
  B00111110, B01001001, B01010101, B01011101, B00001110, // @
  B01111110, B00010001, B00010001, B01111110, B00000000, // A
  B01111111, B01001001, B01001001, B00110110, B00000000, // B
  B00111110, B01000001, B01000001, B00100010, B00000000, // C
  B01111111, B01000001, B01000001, B00111110, B00000000, // D
  B01111111, B01001001, B01001001, B01000001, B00000000, // E
  B01111111, B00001001, B00001001, B00000001, B00000000, // F
  B00111110, B01000001, B01001001, B01111010, B00000000, // G
  B01111111, B00001000, B00001000, B01111111, B00000000, // H
  B01000001, B01111111, B01000001, B00000000, B00000000, // I
  B00110000, B01000000, B01000001, B00111111, B00000000, // J
  B01111111, B00001000, B00010100, B01100011, B00000000, // K
  B01111111, B01000000, B01000000, B01000000, B00000000, // L
  B01111111, B00000010, B00001100, B00000010, B01111111, // M
  B01111111, B00000100, B00001000, B00010000, B01111111, // N
  B00111110, B01000001, B01000001, B00111110, B00000000, // O
  B01111111, B00001001, B00001001, B00000110, B00000000, // P
  B00111110, B01000001, B01000001, B10111110, B00000000, // Q
  B01111111, B00001001, B00001001, B01110110, B00000000, // R
  B01000110, B01001001, B01001001, B00110010, B00000000, // S
  B00000001, B00000001, B01111111, B00000001, B00000001, // T
  B00111111, B01000000, B01000000, B00111111, B00000000, // U
  B00001111, B00110000, B01000000, B00110000, B00001111, // V
  B00111111, B01000000, B00111000, B01000000, B00111111, // W
  B01100011, B00010100, B00001000, B00010100, B01100011, // X
  B00000111, B00001000, B01110000, B00001000, B00000111, // Y
  B01100001, B01010001, B01001001, B01000111, B00000000, // Z
  B01111111, B01000001, B00000000, B00000000, B00000000, // [
  B00000001, B00000110, B00011000, B01100000, B00000000, // \ backslash
  B01000001, B01111111, B00000000, B00000000, B00000000, // ]
  B00000010, B00000001, B00000010, B00000000, B00000000, // hat
  B01000000, B01000000, B01000000, B01000000, B00000000, // _
  B00000001, B00000010, B00000000, B00000000, B00000000, // `
  B00100000, B01010100, B01010100, B01111000, B00000000, // a
  B01111111, B01000100, B01000100, B00111000, B00000000, // b
  B00111000, B01000100, B01000100, B00101000, B00000000, // c
  B00111000, B01000100, B01000100, B01111111, B00000000, // d
  B00111000, B01010100, B01010100, B00011000, B00000000, // e
  B00000100, B01111110, B00000101, B00000000, B00000000, // f
  B10011000, B10100100, B10100100, B01111000, B00000000, // g
  B01111111, B00000100, B00000100, B01111000, B00000000, // h
  B01000100, B01111101, B01000000, B00000000, B00000000, // i
  B01000000, B10000000, B10000100, B01111101, B00000000, // j
  B01111111, B00010000, B00101000, B01000100, B00000000, // k
  B01000001, B01111111, B01000000, B00000000, B00000000, // l
  B01111100, B00000100, B01111100, B00000100, B01111000, // m
  B01111100, B00000100, B00000100, B01111000, B00000000, // n
  B00111000, B01000100, B01000100, B00111000, B00000000, // o
  B11111100, B00100100, B00100100, B00011000, B00000000, // p
  B00011000, B00100100, B00100100, B11111100, B00000000, // q
  B01111100, B00001000, B00000100, B00000100, B00000000, // r
  B01001000, B01010100, B01010100, B00100100, B00000000, // s
  B00000100, B00111111, B01000100, B00000000, B00000000, // t
  B00111100, B01000000, B01000000, B01111100, B00000000, // u
  B00011100, B00100000, B01000000, B00100000, B00011100, // v
  B00111100, B01000000, B00111100, B01000000, B00111100, // w
  B01000100, B00101000, B00010000, B00101000, B01000100, // x
  B10011100, B10100000, B10100000, B01111100, B00000000, // y
  B01100100, B01010100, B01001100, B00000000, B00000000, // z
  B00001000, B00110110, B01000001, B00000000, B00000000, // {
  B01111111, B00000000, B00000000, B00000000, B00000000, // |
  B01000001, B00110110, B00001000, B00000000, B00000000, // }
  B00001000, B00000100, B00001000, B00000100, B00000000, // ~
};



byte clessidra[8] = {B11111000,
                     B10001000,
                     B01110000,
                     B00100000,
                     B00100000,
                     B01010000,
                     B10101000,
                     B11111000
                    };

byte one[8] = {B00100000,
               B01100000,
               B00100000,
               B00100000,
               B00100000,
               B01110000,
               B00000000,
               B00000000
              };

byte two[8] = {B11110000,
               B10001000,
               B00010000,
               B00100000,
               B01000000,
               B11111000,
               B00000000,
               B00000000
              };

byte three[8] = {B11110000,
                 B00001000,
                 B00110000,
                 B00001000,
                 B00001000,
                 B11110000,
                 B00000000,
                 B00000000
                };
byte four[8] = {B00110000,
                B01010000,
                B10010000,
                B11111000,
                B00010000,
                B00010000,
                B00000000,
                B00000000
               };

byte five[8] = {B11111000,
                B10000000,
                B11110000,
                B00001000,
                B00001000,
                B11110000,
                B00000000,
                B00000000
               };

byte six[8] = {B01111000,
               B10000000,
               B11110000,
               B10001000,
               B10001000,
               B01110000,
               B00000000,
               B00000000
              };

byte seven[8] = {B11111000,
                 B00001000,
                 B00010000,
                 B00010000,
                 B00100000,
                 B00100000,
                 B00000000,
                 B00000000
                };

byte eight[8] = {B01110000,
                 B10001000,
                 B01110000,
                 B10001000,
                 B10001000,
                 B01110000,
                 B00000000,
                 B00000000
                };

byte nine[8] = {B01110000,
                B10001000,
                B01111000,
                B00001000,
                B00001000,
                B01110000,
                B00000000,
                B00000000
               };

byte zero[8] = {B01110000,
                B10001000,
                B10001000,
                B10001000,
                B10001000,
                B01110000,
                B00000000,
                B00000000
               };

byte wipe[8] = {B00000000,
                B00000000,
                B00000000,
                B00000000,
                B00000000,
                B00000000,
                B00000000,
                B00000000
               };


LedControl lc = LedControl(11, 13, 10, 4);
//---------------------data-clk-cs-devices°

unsigned long delaytime = 1;
boolean sandIsUp = false;
int seconds = 0;
byte firstDigitFH[8];
byte firstDigitSH[8];
byte secondDigitFH[8];
byte secondDigitSH[8];
byte thirdDigitFH[8];
byte thirdDigitSH[8];
byte fourthDigit[8];
byte colBuffer[64];

boolean showClock = true;
int shiftsCount = 0;
int recivedCount = 0;
boolean isCommand = false;
String command;

void setup() {
  Serial.begin(115200);
  int devices = lc.getDeviceCount();
  //init and test
  for (int address = 0; address < devices; address++) {
    lc.shutdown(address, false);
    lc.setIntensity(address, LED_INTENSITY);
    lc.clearDisplay(address);
  }

  setTime(21, 40, 00, 19, 11, 2016);
  drawClock();
  flushBuffer();
}

void loop() {
  while (Serial.available() > 0) {
    char c = Serial.read();
    if (isCommand) {
      command.concat(c);
      continue;
    }
    if (c == '~') {
      isCommand = true;
      command = "";
      continue;
    }
    shiftsCount = 0;
    status2Buf();

    c = c - 32;
    memcpy(colBuffer + 35, CH + 5 * c, 5);
    for (int i = 0; i < 6; i++) {
      shiftDraw();
    }
    showClock = false;
    recivedCount++;

    if (recivedCount > 59) {
      Serial.println("c");
      recivedCount = 0;
    }
  }
  if (isCommand) {
    isCommand = false;
    processCommand(command);
  }
  recivedCount = 0;
  if (!showClock && shiftsCount < 20) {
    if (shiftsCount == 19) { //end of scrolling text
      //here print clock at the end of buffer and scroll 32 times
      drawClockOnBuffer();
      for (int i = 0; i < 32; i++) {
        shiftDrawCompleteBuffer();
      }
    } else shiftDraw();
    shiftsCount++;
    return;
  }
  blinkDots();
  moveSand();
  blinkWeekDays();
  sandIsUp = !sandIsUp;
  delay(999);

  if (seconds > second()) {
    seconds = 0;
    clearDigits();
    drawTime();
  }
  seconds = second();
}

void processCommand(String command) {
  switch (command.charAt(0)) {
    case 't': setTimeFromString(command.substring(1)); break;
    case 'b': for(int i=0; i<4; i++) { lc.setIntensity(i, command.substring(1).toInt());} break;
    default: Serial.println("default");
  }
}

void setTimeFromString(String s) {
  time_t pctime = 0;
  for (int i = 0; i < 10; i++) {
    char c = s.charAt(i);
    if ( c >= '0' && c <= '9') {
      pctime = (10 * pctime) + (c - '0') ; // convert digits to a number
    }
  }
  setTime(pctime);
}

void blinkWeekDays() {
  switch (weekday()) {
    case 1: blinkAsSunday(); return;
    case 2: blinkAsMonday(); return;
    case 3: blinkAsTuesday(); return;
    case 4: blinkAsWednesday(); return;
    case 5: blinkAsThursday(); return;
    case 6: blinkAsFriday(); return;
    case 7: blinkAsSaturday(); return;
  }
}
//scorrimento

//

void flushBuffer() {
  for (int i = 0; i < 64; i++) {
    colBuffer[i] = 0;
  }
}

void drawBuffer() {
  for (int i = 0; i < 32; i++) {
    drawColumn(i, colBuffer[i]);
  }
}

void drawBuffer32() {
  for (int i = 32; i < 64; i++) {
    drawColumn(i - 32, colBuffer[i]);
  }
}

void shiftDraw() {
  for (int i = 0; i < 39; i++) {
    colBuffer[i] = colBuffer[i + 1];
  }
  colBuffer[39] = B00000000;
  drawBuffer();
  delay(SCROLL_DELAY);
}

void shiftCompleteBuffer(int shift) {
  for (int i = 0; i < 63 - shift; i++) {
    colBuffer[i] = colBuffer[i + shift];
  }
  for (int i = 0; i < shift; i++) {
    colBuffer[63 - i];
  }
  drawBuffer();
}

void shiftDrawCompleteBuffer() {
  for (int i = 0; i < 63; i++) {
    colBuffer[i] = colBuffer[i + 1];
  }
  colBuffer[63] = B00000000;
  drawBuffer();
  delay(SCROLL_DELAY);
}

void drawClessidra() {
  byte mask = 1;
  int i = 0;
  int j = 0;
  byte draw[8] = {B11111000,
                  B10001000,
                  B01110000,
                  B00100000,
                  B00100000,
                  B01010000,
                  B10101000,
                  B11111000
                 };
  for (j = 0; j < 8; j++) {
    for (mask = 10000000; mask > 0; mask >>= 1) {
      if (draw[j] & mask) {
        lc.setLed(0, j, i, true);
      } else {
        lc.setLed(0, j, i, false);
      }
      if (i == 7) {
        i = 0;
      } else {
        i++;
      }
    }
  }
}


void moveSand() {
  lc.setLed(0, 6, 2, sandIsUp);
  lc.setLed(0, 5, 2, !sandIsUp);
}

void drawDays() {
  lc.setLed(1, 7, 1, true);
  lc.setLed(1, 7, 2, true);
  lc.setLed(1, 7, 4, true);
  lc.setLed(1, 7, 5, true);
  lc.setLed(1, 7, 7, true);

  lc.setLed(2, 7, 0, true);
  lc.setLed(2, 7, 2, true);
  lc.setLed(2, 7, 3, true);
  lc.setLed(2, 7, 5, true);
  lc.setLed(2, 7, 6, true);

  lc.setLed(3, 7, 0, true);
  lc.setLed(3, 7, 1, true);
  lc.setLed(3, 7, 3, true);
  lc.setLed(3, 7, 4, true);
}

void blinkAsSunday() {
  lc.setLed(3, 7, 3, sandIsUp);
  lc.setLed(3, 7, 4, sandIsUp);
}

void blinkAsMonday() {
  lc.setLed(1, 7, 1, sandIsUp);
  lc.setLed(1, 7, 2, sandIsUp);
}

void blinkAsTuesday() {
  lc.setLed(1, 7, 4, sandIsUp);
  lc.setLed(1, 7, 5, sandIsUp);
}

void blinkAsWednesday() {
  lc.setLed(1, 7, 3, sandIsUp);
  lc.setLed(2, 7, 4, sandIsUp);
}

void blinkAsThursday() {
  lc.setLed(2, 7, 2, sandIsUp);
  lc.setLed(2, 7, 3, sandIsUp);
}

void blinkAsFriday() {
  lc.setLed(2, 7, 5, sandIsUp);
  lc.setLed(2, 7, 6, sandIsUp);
}

void blinkAsSaturday() {
  lc.setLed(3, 7, 0, sandIsUp);
  lc.setLed(3, 7, 1, sandIsUp);
}


void blinkDots() {
  lc.setLed(2, 1, 2, sandIsUp);
  lc.setLed(2, 4, 2, sandIsUp);
}

void draw8x8(byte draw[8], int device) {
  byte mask = 1;
  int i = 0;
  int j = 0;
  for (j = 0; j < 8; j++) {
    for (mask = 10000000; mask > 0; mask >>= 1) {
      if (draw[j] & mask) {
        lc.setLed(device, j, i, true);
      }
      if (i == 7) {
        i = 0;
      } else {
        i++;
      }
    }
  }
}

void drawFirstDigit(byte * number) {
  shiftSave(number, firstDigitFH, 6, true);
  shiftSave(number, firstDigitSH, 2, false);
  draw8x8(firstDigitFH, 0);
  draw8x8(firstDigitSH, 1);
}

void drawSecondDigit (byte * number) {
  shiftSave(number, secondDigitFH, 4, true);
  shiftSave(number, secondDigitSH, 4, false);
  draw8x8(secondDigitFH, 1);
  draw8x8(secondDigitSH, 2);
}

void drawThirdDigit(byte * number) {
  shiftSave(number, thirdDigitFH, 4, true);
  shiftSave(number, thirdDigitSH, 4, false);
  draw8x8(thirdDigitFH, 2);
  draw8x8(thirdDigitSH, 3);
}

void drawFourthDigit(byte * number) {
  shiftSave(number, fourthDigit, 2, true);
  draw8x8(fourthDigit, 3);
}

void shiftSave(byte * in, byte * out, int shift, boolean right) {
  int i;
  if (right) {
    for (i = 0; i < 8; i++) {
      out[i] = in[i] >> shift;
    }
    return;
  }
  for (i = 0; i < 8; i++) {
    out[i] = in[i] << shift;
  }
}

void drawTime() {
  //hours
  if (hour() < 10) {
    drawFirstDigit(zero);
  }
  if (hour() > 9 && hour() < 20) {
    drawFirstDigit(one);
  }
  if (hour() > 19) {
    drawFirstDigit(two);
  }

  drawSecondDigit(switchDigit(hour() % 10));

  //minutes
  int thirdDigit = (minute() - (minute() % 10)) / 10;
  int fourthDigit = (minute() % 10);

  drawThirdDigit(switchDigit(thirdDigit));
  drawFourthDigit(switchDigit(fourthDigit));
}

byte* switchDigit(int d) {
  switch (d) {
    case 0: return zero; break;
    case 1: return one; break;
    case 2: return two; break;
    case 3: return three; break;
    case 4: return four; break;
    case 5: return five; break;
    case 6: return six; break;
    case 7: return seven; break;
    case 8: return eight; break;
    case 9: return nine; break;
  }
}

void clearDigits() {
  int i, j;
  for (j = 0; j < 6; j++) {
    for (i = 6; i < 32; i++) {
      setLedUnbounded(j, i, false);
    }
  }
}

void setLedUnbounded(int j, int i, boolean value) {
  if (i < 8) {
    lc.setLed(0, j, i, value);
    return;
  }
  if (i < 16) {
    lc.setLed(1, j, i - 8, value);
    return;
  }
  if (i < 24) {
    lc.setLed(2, j, i - 16, value);
    return;
  }
  if (i < 32) {
    lc.setLed(3, j, i - 24, value);
    return;
  }
}

void clearWhole() {
  for (int i = 0; i < 4; i++) {
    lc.clearDisplay(i);
  }
}

void drawColumn(int col, byte val) {
  int row = 7;
  for (byte mask = B10000000; mask != 0; mask >>= 1) {
    if (val & mask) {
      setLedUnbounded(row, col, true);
    }
    else {
      setLedUnbounded(row, col, false);
    }
    row--;
  }
}

void drawStringStatic(char* s, int length) {
  for (int i = 0; i < length; i++) {
    char c = s[i] - 32;
    memcpy_P(colBuffer + (i * 5), CH + 5 * c, 5);
  }
  for (int i = 0; i < (length * 5); i++) {
    drawColumn(i, colBuffer[i]);
  }
}

void fakeSetLedUnbounded(int j, int i, boolean value) {
  if (i < 8) {
    lc.fakeSetLed(0, j, i, value);
    return;
  }
  if (i < 16) {
    lc.fakeSetLed(1, j, i - 8, value);
    return;
  }
  if (i < 24) {
    lc.fakeSetLed(2, j, i - 16, value);
    return;
  }
  if (i < 32) {
    lc.fakeSetLed(3, j, i - 24, value);
    return;
  }
}

void fakeDrawColumn(int col, byte val) {
  int row = 7;
  for (byte mask = B10000000; mask != 0; mask >>= 1) {
    if (val & mask) {
      fakeSetLedUnbounded(row, col, true);
    }
    else {
      fakeSetLedUnbounded(row, col, false);
    }
    row--;
  }
}

void fakeDrawBuffer() {
  for (int i = 0; i < 32; i++) {
    fakeDrawColumn(i, colBuffer[i]);
  }
}

void status2Buf() {
  for (int j = 0; j < 4; j++) {
    for (int i = 0; i < 32; i++) {
      colBuffer[i] = lc.status[i];
    }
    fakeDrawBuffer();
  }
}

void drawClockOnBuffer() {
  drawClessidraOnBuffer();
  drawDaysOnBuffer();
  drawTimeOnBuffer();
}

void draw8x8OnBuffer(byte draw[8], int device) {
  byte mask;
  int i = 0;
  int j = 0;
  for (j = 0; j < 8; j++) {
    for (mask = 10000000; mask > 0; mask >>= 1) {
      if (draw[j] & mask) {
        bitWrite(colBuffer[device * 8 + i], j, 1);
      } else {
        bitWrite(colBuffer[32 + device * 8 + i], j, 0);
      }
      if (i == 7) {
        i = 0;
      } else {
        i++;
      }
    }
  }
}

void drawFirstDigitOnBuffer(byte * number) {
  shiftSave(number, firstDigitFH, 6, true);
  shiftSave(number, firstDigitSH, 2, false);
  draw8x8OnBuffer(firstDigitFH, 4);
  draw8x8OnBuffer(firstDigitSH, 5);
}

void drawSecondDigitOnBuffer (byte * number) {
  shiftSave(number, secondDigitFH, 4, true);
  shiftSave(number, secondDigitSH, 4, false);
  draw8x8OnBuffer(secondDigitFH, 5);
  draw8x8OnBuffer(secondDigitSH, 6);
}

void drawThirdDigitOnBuffer(byte * number) {
  shiftSave(number, thirdDigitFH, 4, true);
  shiftSave(number, thirdDigitSH, 4, false);
  draw8x8OnBuffer(thirdDigitFH, 6);
  draw8x8OnBuffer(thirdDigitSH, 7);
  forceDrawThirdDigit();
}

void drawFourthDigitOnBuffer(byte * number) {
  shiftSave(number, fourthDigit, 2, true);
  draw8x8OnBuffer(fourthDigit, 7);
}

void drawTimeOnBuffer() {
  //hours
  if (hour() < 10) {
    drawFirstDigitOnBuffer(zero);
  }
  if (hour() > 9 && hour() < 20) {
    drawFirstDigitOnBuffer(one);
  }
  if (hour() > 19) {
    drawFirstDigitOnBuffer(two);
  }

  drawSecondDigitOnBuffer(switchDigit(hour() % 10));

  //minutes
  int thirdDigit = (minute() - (minute() % 10)) / 10;
  int fourthDigit = (minute() % 10);

  drawThirdDigitOnBuffer(switchDigit(thirdDigit));
  forceDrawThirdDigit();
  drawFourthDigitOnBuffer(switchDigit(fourthDigit));
}

void drawClessidraOnBuffer() {
  byte draw[8] = {B11111000,
                  B10001000,
                  B01110000,
                  B00100000,
                  B00100000,
                  B01010000,
                  B10101000,
                  B11111000
                 };
  draw8x8OnBuffer(draw, 4);
}

void setLedOnBuffer(int device, int row, int column, boolean foo) {
  int b = 1;
  if (!foo) {
    b = 0;
  }
  bitWrite(colBuffer[8 * device + 32 + column], 7, b);
}

void drawDaysOnBuffer() {
  setLedOnBuffer(1, 7, 1, true);
  setLedOnBuffer(1, 7, 2, true);
  setLedOnBuffer(1, 7, 4, true);
  setLedOnBuffer(1, 7, 5, true);
  setLedOnBuffer(1, 7, 7, true);

  setLedOnBuffer(2, 7, 0, true);
  setLedOnBuffer(2, 7, 2, true);
  setLedOnBuffer(2, 7, 3, true);
  setLedOnBuffer(2, 7, 5, true);
  setLedOnBuffer(2, 7, 6, true);

  setLedOnBuffer(3, 7, 0, true);
  setLedOnBuffer(3, 7, 1, true);
  setLedOnBuffer(3, 7, 3, true);
  setLedOnBuffer(3, 7, 4, true);
}

void drawClock() {
  drawClessidra();
  drawDays();
  drawTime();
}

void forceDrawThirdDigit() {
  //this is a workaround for a probable bug
  //occurring when drawing third digit on external buffer for scroll back purpose
  //i'm trying to optimize as much as i can
  switch ((minute() - (minute() % 10)) / 10) {
    case 0: bitWrite(colBuffer[52], 3, 1); bitWrite(colBuffer[52], 4, 1); bitWrite(colBuffer[53], 5, 1); bitWrite(colBuffer[54], 5, 1); bitWrite(colBuffer[55], 0, 1); return;
    case 1: bitWrite(colBuffer[53], 5, 1); bitWrite(colBuffer[54], 4, 1); bitWrite(colBuffer[54], 3, 1); bitWrite(colBuffer[54], 2, 1); bitWrite(colBuffer[54], 1, 1); bitWrite(colBuffer[55], 5, 1); return;
    case 2: bitWrite(colBuffer[55], 0, 1); bitWrite(colBuffer[55], 2, 1); bitWrite(colBuffer[54], 5, 1);   bitWrite(colBuffer[54], 3, 1); bitWrite(colBuffer[53], 4, 1); bitWrite(colBuffer[53], 5, 1); bitWrite(colBuffer[52], 5, 1); return;
    case 3: bitWrite(colBuffer[55], 0, 1); bitWrite(colBuffer[55], 2, 1); bitWrite(colBuffer[54], 5, 1); bitWrite(colBuffer[53], 5, 1); bitWrite(colBuffer[52], 5, 1); bitWrite(colBuffer[54], 2, 1); return;
    case 4: colBuffer[55] = B00111111; bitWrite(colBuffer[54], 3, 1); bitWrite(colBuffer[53], 3, 1); bitWrite(colBuffer[52], 3, 1); return;
    case 5: bitWrite(colBuffer[55], 0, 1); bitWrite(colBuffer[55], 2, 1); bitWrite(colBuffer[54], 2, 1); bitWrite(colBuffer[54], 5, 1); bitWrite(colBuffer[53], 2, 1); bitWrite(colBuffer[53], 5, 1); bitWrite(colBuffer[52], 5, 1); return;
  }
}

