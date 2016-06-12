package Main;

public class Directory {
	
	public static int index;
	public static String code;
		
	public static void setMainProgram(String mainProgram) {
		MainProgram = mainProgram;
	}
	
	public static String HeaderFile=
			"#include \"usbstk5515_gpio.h\"\n" + 
			"#include \"usbstk5515_i2c.h\"";
	
	public static String GlobleVariable="\n#define AIC3204_I2C_ADDR 0x18\n" + 
			"#define Rcv 0x08\n" + 
			"#define Xmit 0x20\n"+
			"#define XmitL 0x10\n" + 
			"#define XmitR 0x20\n"+
			"#define freq_6_857 0xF1\n" + 
			"#define freq_8 0xE1\n" + 
			"#define freq_9_6 0xD1\n" + 
			"#define freq_12 0xC1\n" + 
			"#define freq_16 0xB1\n" + 
			"#define freq_24 0xA1\n" + 
			"#define freq_48 0x91";
	
	
	public static String Function="\nInt16 AIC3204_rget(  Uint16 regnum, Uint16* regval )\n" + 
			"{\n" + 
			"Int16 retcode = 0;\n" + 
			"Uint8 cmd[2];\n" + 
			"\n" + 
			"cmd[0] = regnum & 0x007F;       // 7-bit Register Address\n" + 
			"cmd[1] = 0;\n" + 
			"\n" + 
			"retcode |= USBSTK5515_I2C_write( AIC3204_I2C_ADDR, cmd, 1 );\n" + 
			"retcode |= USBSTK5515_I2C_read( AIC3204_I2C_ADDR, cmd, 1 );\n" + 
			"\n" + 
			"*regval = cmd[0];\n" + 
			"USBSTK5515_wait( 10 );\n" + 
			"return retcode;\n" + 
			"}\n" + 
			"\n" + 
			"Int16 AIC3204_rset( Uint16 regnum, Uint16 regval )\n" + 
			"{\n" + 
			"Uint8 cmd[2];\n" + 
			"cmd[0] = regnum & 0x007F;       // 7-bit Register Address\n" + 
			"cmd[1] = regval;                // 8-bit Register Data\n" + 
			"\n" + 
			"return USBSTK5515_I2C_write( AIC3204_I2C_ADDR, cmd, 2 );\n" + 
			"} \n" + 
			"\n" + 
			"void AIC3204_config(Uint8 sampling_freq)\n" + 
			"{\n" + 
			"	/* Configure AIC3204 */\n" + 
			"AIC3204_rset( 0, 0 );          // Select page 0\n" + 
			"AIC3204_rset( 1, 1 );          // Reset codec\n" + 
			"AIC3204_rset( 0, 1 );          // Select page 1\n" + 
			"AIC3204_rset( 1, 8 );          // Disable crude AVDD generation from DVDD\n" + 
			"AIC3204_rset( 2, 1 );          // Enable Analog Blocks, use LDO power\n" + 
			"AIC3204_rset( 0, 0 );          // Select page 0\n" + 
			"/* PLL and Clocks config and Power Up  */\n" + 
			"AIC3204_rset( 27, 0x0d );      // BCLK and WCLK is set as o/p to AIC3204(Master)\n" + 
			"AIC3204_rset( 28, 0x00 );      // Data ofset = 0\n" + 
			"AIC3204_rset( 4, 3 );          // PLL setting: PLLCLK <- MCLK, CODEC_CLKIN <-PLL CLK\n" + 
			"AIC3204_rset( 6, 7 );          // PLL setting: J=7\n" + 
			"AIC3204_rset( 7, 0x06 );       // PLL setting: HI_BYTE(D=1680)\n" + 
			"AIC3204_rset( 8, 0x90 );       // PLL setting: LO_BYTE(D=1680)\n" + 
			"AIC3204_rset( 30, 0x88 );      // For 32 bit clocks per frame in Master mode ONLY\n" + 
			"                               // BCLK=DAC_CLK/N =(12288000/8) = 1.536MHz = 32*fs\n" + 
			"AIC3204_rset( 5, sampling_freq);       // PLL setting: Power up PLL, P=1 and R=1\n" + 
			"AIC3204_rset( 13, 0 );         // Hi_Byte(DOSR) for DOSR = 128 decimal or 0x0080 DAC oversamppling\n" + 
			"AIC3204_rset( 14, 0x80 );      // Lo_Byte(DOSR) for DOSR = 128 decimal or 0x0080\n" + 
			"AIC3204_rset( 20, 0x80 );      // AOSR for AOSR = 128 decimal or 0x0080 for decimation filters 1 to 6\n" + 
			"AIC3204_rset( 11, 0x82 );      // Power up NDAC and set NDAC value to 2\n" + 
			"AIC3204_rset( 12, 0x87 );      // Power up MDAC and set MDAC value to 7\n" + 
			"AIC3204_rset( 18, 0x87 );      // Power up NADC and set NADC value to 7\n" + 
			"AIC3204_rset( 19, 0x82 );      // Power up MADC and set MADC value to 2\n" + 
			"/* DAC ROUTING and Power Up */\n" + 
			"AIC3204_rset(  0, 0x01 );      // Select page 1\n" + 
			"AIC3204_rset( 12, 0x08 );      // LDAC AFIR routed to HPL\n" + 
			"AIC3204_rset( 13, 0x08 );      // RDAC AFIR routed to HPR\n" + 
			"AIC3204_rset(  0, 0x00 );      // Select page 0\n" + 
			"AIC3204_rset( 64, 0x02 );      // Left vol=right vol\n" + 
			"AIC3204_rset( 65, 0x00 );      // Left DAC gain to 0dB VOL; Right tracks Left\n" + 
			"AIC3204_rset( 63, 0xd4 );      // Power up left,right data paths and set channel\n" + 
			"AIC3204_rset(  0, 0x01 );      // Select page 1\n" + 
			"AIC3204_rset( 16, 0x00 );      // Unmute HPL , 0dB gain\n" + 
			"AIC3204_rset( 17, 0x00 );      // Unmute HPR , 0dB gain\n" + 
			"AIC3204_rset(  9, 0x30 );      // Power up HPL,HPR\n" + 
			"AIC3204_rset(  0, 0x00 );      // Select page 0\n" + 
			"USBSTK5515_wait( 500 );        // Wait\n" + 
			"\n" + 
			"/* ADC ROUTING and Power Up */\n" + 
			"AIC3204_rset( 0, 1 );          // Select page 1\n" + 
			"AIC3204_rset( 0x34, 0x30 );    // STEREO 1 Jack\n" + 
			"		                        // IN2_L to LADC_P through 40 kohm\n" + 
			"AIC3204_rset( 0x37, 0x30 );    // IN2_R to RADC_P through 40 kohmm\n" + 
			"AIC3204_rset( 0x36, 3 );       // CM_1 (common mode) to LADC_M through 40 kohm\n" + 
			"AIC3204_rset( 0x39, 0xc0 );    // CM_1 (common mode) to RADC_M through 40 kohm\n" + 
			"AIC3204_rset( 0x3b, 0 );       // MIC_PGA_L unmute\n" + 
			"AIC3204_rset( 0x3c, 0 );       // MIC_PGA_R unmute\n" + 
			"AIC3204_rset( 0, 0 );          // Select page 0\n" + 
			"AIC3204_rset( 0x51, 0xc0 );    // Powerup Left and Right ADC\n" + 
			"AIC3204_rset( 0x52, 0 );       // Unmute Left and Right ADC\n" + 
			"\n" + 
			"AIC3204_rset( 0, 0 );    \n" + 
			"USBSTK5515_wait( 200 );        // Wait\n" + 
			"/* I2S settings */\n" + 
			"I2S0_SRGR = 0x0;\n" + 
			"I2S0_CR = 0x8010;    // 16-bit word, slave, enable I2C\n" + 
			"I2S0_ICMR = 0x3f;    // Enable interrupts\n" + 
			"\n" + 
			"}";
	
	public static String Initializevariable="/* Initialize BSL */\n" + 
			"    SYS_EXBUSSEL = 0x6100;\n" + 
			"    USBSTK5515_init( );\n";
	
	public static String MainProgram="\nSYS_EXBUSSEL = 0x6100;\n" + 
			"USBSTK5515_init( );\n"
			+ "AIC3204_config(freq_48);";
	
	public static String Ending ="/* Disble I2S */\n" + 
			"  I2S0_CR = 0x00;\n" + 
			"  return 0;\n" + 
			"}\n" ;
	public static String variablevalue ="$";
	public static String InputVariable="$";
	public static String matrixVariable="$";
	public static String InputOutputVariable="$";


}
