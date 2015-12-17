#ifndef ARVORE_H
#define ARVORE_H
#include "node.h"
#include <iostream>
class Arvore
{
public:
    Arvore();
private:
    Node *raiz;
public:
    void criarArvore();
    void central(Node *node);
    Node * getRaiz();
};

#endif // ARVORE_H
