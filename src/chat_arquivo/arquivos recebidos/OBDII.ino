#define CHAVE 0x7B
#define STR 0x73
#define NULL 0x00

/** Padrão e ponteiro para o padrão **/
const char gpsReceiveRmcCAChr[] = { "$GPRMC,{str},{str},{str},{str},{str},{str},{str},{str},{str},{str},{str}*" };

const char *gpsReceiveRmcCPChr = &gpsReceiveRmcCAChr[0];


bool strPattern(char);

/** Ponteiro de função para filtrar 
dependendo de cada padrão **/
bool (*filter)(char);
bool filterOn = false;


/** Variáveis **/
char **ponteiroVariaveis;
char *variaveis[11];
char timeGAChr[] = {"000000"}; 
char warningGAChr[] = {"0"};
char latitudeGAChr[] = {"0000000"}; 
char latitudePosGAChr[] = {"0"};
char longitudeGAChr[] = {"00000000"};
char longitudePosGAChr[] = {"0"};
char speedGAChr[] = {"00000"};
char angleGAChr[] = {"00000"};
char dateGAChr[] = {"000000"};
char magVarGAChr[] = {"00000"};
char magVarPosGAChr[] = {"0"};

void setup () {
  Serial.begin( 9600 );
  filterOn = false;
  initialize();
}

void loop () {
  
}

void serialEvent() 
{
  char dado = (char) Serial.read();

  gpsReceive(dado);
 
}

/** Inicializa o ponteiro para as variaveis **/
void initialize() {
  
  variaveis[ 0 ] = &timeGAChr[ 0 ];
  variaveis[ 1 ] = &warningGAChr[ 0 ];
  variaveis[ 2 ] = &latitudeGAChr[ 0 ];
  variaveis[ 3 ] = &latitudePosGAChr[ 0 ];
  variaveis[ 4 ] = &longitudeGAChr[ 0 ];
  variaveis[ 5 ] = &longitudePosGAChr[ 0 ];
  variaveis[ 6 ] = &speedGAChr[ 0 ];
  variaveis[ 7 ] = &angleGAChr[ 0 ];
  variaveis[ 8 ] = &dateGAChr[ 0 ];
  variaveis[ 9 ] = &magVarGAChr[ 0 ];
  variaveis[ 10 ] = &magVarPosGAChr[ 0 ];
  ponteiroVariaveis = &(variaveis[ 0 ]);
}


void gpsReceive(char dado) {
  
  if( *gpsReceiveRmcCPChr == dado && filterOn == true ) {
    
    filterOn = false;
    gpsReceiveRmcCPChr += 1;
    ponteiroVariaveis += 1; // Para a próxima variável
    
    if(*gpsReceiveRmcCPChr == NULL )  {
          gpsReceiveRmcCPChr = &gpsReceiveRmcCAChr[ 0 ];
      	  initialize();
      	  for(int i = 0; i < 11; i++) {
     	    for(int j = 0; *(variaveis[i] +j); j++) {
              Serial.print(*(variaveis[i] +j));
            }
     	    Serial.println();  
          }    
    }  
  }
  else if ( *gpsReceiveRmcCPChr == dado && !filterOn ) 
  {     
    	gpsReceiveRmcCPChr += 1;
    	if(*gpsReceiveRmcCPChr == NULL)  {
          gpsReceiveRmcCPChr = &gpsReceiveRmcCAChr[ 0 ]; 
        }
  } 
  else 
  {  
      if(*gpsReceiveRmcCPChr == CHAVE ) 
      {   
        filterOn = true;  // "Filtro ligado"
        gpsReceiveRmcCPChr += 1; // Se desloca para a direita a procura do "tipo" do modificador
        if(*gpsReceiveRmcCPChr == STR) { // É modificador str      
          filter = strPattern;           
          gpsReceiveRmcCPChr += 4; // Se desloca para depois do '}'
        } 
      }   
      if(filterOn) 
      {
        if((*filter)(dado))   {
          
          **ponteiroVariaveis = dado;  
          *ponteiroVariaveis = *ponteiroVariaveis + 1;
        }
      }
      else {
        gpsReceiveRmcCPChr = &gpsReceiveRmcCAChr[ 0 ];
      }
  }
}

bool strPattern(char dado) {
  
  if((dado >= 0x41 && dado <= 0x5A) || 
  (dado >= 0x61 && dado <= 0x7A) ||
  (dado >= 0x30 && dado <= 0x39) ||
  (dado == 0x2E)) {
      
    return true;
  }
  
  return false;
}

