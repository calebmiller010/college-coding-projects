#include "header.h"


int main(int argc, char* arg[]) {
    int i;
    int s;
    int n;
    char* servName;
    int servPort;
    char* string;
    int len;
    int maxLen;
    char buffer[257];
    char* ptr = buffer;
    struct sockaddr_in servAddr;
    
    if(argc != 4) {
        printf("Error: three arguments needed\n");
        exit(1);
    }
    
    servName = arg[1];
    servPort = atoi(arg[2]);
    string = arg[3];
    
    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin_family = AF_INET;
    inet_pton(AF_INET,servName,&servAddr.sin_addr);
    servAddr.sin_port = htons(servPort);
    
    if((s = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Error: socket creation failed!");
        exit(1);
    }
    
    if(connect(s,(struct sockaddr*)&servAddr,sizeof(servAddr)) < 0) {
        perror("Error: connection failed!");
        exit(1);
    }
   
    send(s, string, strlen(string), 0);
    recv(s,ptr,sizeof(buffer),0);
    fputs(buffer,stdout);  
    close(s);
    exit(0);
}
