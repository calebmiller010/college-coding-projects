#include "header.h"

int main(int argc, char* argv[]) {
    int ls;
    int s;
    char buffer[256];
    char *ptr = buffer;
    int len = 0;
    int maxLen;
    int n = 0;
    int waitSize = 16;
    struct sockaddr_in servAddr;
    struct sockaddr_in clientAddr;
    int clntAddrLen;
    char serverstring[256];
    char* string = serverstring;
    int portNum;

    if(argc != 3) {
        printf("Error: two arguments needed\n");
        exit(1);
    }
    portNum = atoi(argv[1]);
    string = argv[2];
    memset(&servAddr,0,sizeof(servAddr));
    servAddr.sin_family = AF_INET;
    servAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    servAddr.sin_port = htons(portNum);
    
    if((ls = socket(PF_INET,SOCK_STREAM,0)) < 0 ) {
        perror("Error: Listen socket failed!");
        exit(1);
    }
    
    if(bind(ls,(struct sockaddr *)&servAddr,sizeof(servAddr)) < 0) {
        perror("Error: binding failed!");
        exit(1);
    }
    
    if(listen(ls, waitSize) < 0) {
        perror("Error: listening failed!");
        exit(1);
    }
    

    for(;;) {
        
        memset(buffer,0,sizeof(buffer));
        memset(serverstring,0,sizeof(serverstring));
        
        if((s = accept(ls, (struct sockaddr*)&clientAddr, &clntAddrLen)) < 0) {
            perror("Error: accepting failed!");
            exit(1);
        }
        /*if(fcntl(s, F_SETFL, fcntl(s, F_GETFL) | O_NONBLOCK) < 0) {
            perror("Error: non-blocking failed!");
            exit(1);
        }*/
        
        n = recv(s,ptr,maxLen,0);
        sprintf(buffer,"%s %s \n", ptr, string);
        send(s,buffer,sizeof(buffer),0);
        close(s);
    }
}
