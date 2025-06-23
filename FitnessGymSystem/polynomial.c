#include <stdio.h>
#include <stdlib.h>

// 定义多项式项的结构
typedef struct Term {
    int coefficient;  // 系数
    int exponent;     // 指数
    struct Term* next;
} Term;

// 定义多项式结构
typedef struct Polynomial {
    Term* head;
    int degree;
} Polynomial;

// 创建新的项
Term* createTerm(int coeff, int exp) {
    Term* newTerm = (Term*)malloc(sizeof(Term));
    if (newTerm == NULL) {
        printf("内存分配失败！\n");
        exit(1);
    }
    newTerm->coefficient = coeff;
    newTerm->exponent = exp;
    newTerm->next = NULL;
    return newTerm;
}

// 创建多项式
Polynomial* createPolynomial() {
    Polynomial* poly = (Polynomial*)malloc(sizeof(Polynomial));
    if (poly == NULL) {
        printf("内存分配失败！\n");
        exit(1);
    }
    poly->head = NULL;
    poly->degree = -1;
    return poly;
}

// 向多项式添加项（按指数降序排列）
void addTerm(Polynomial* poly, int coeff, int exp) {
    if (coeff == 0) return; // 系数为0的项不添加
    
    Term* newTerm = createTerm(coeff, exp);
    
    // 如果多项式为空
    if (poly->head == NULL) {
        poly->head = newTerm;
        poly->degree = exp;
        return;
    }
    
    // 如果新项的指数大于头项
    if (exp > poly->head->exponent) {
        newTerm->next = poly->head;
        poly->head = newTerm;
        poly->degree = exp;
        return;
    }
    
    // 在适当位置插入
    Term* current = poly->head;
    Term* prev = NULL;
    
    while (current != NULL && current->exponent >= exp) {
        if (current->exponent == exp) {
            // 合并同类项
            current->coefficient += coeff;
            if (current->coefficient == 0) {
                // 如果系数变为0，删除该项
                if (prev == NULL) {
                    poly->head = current->next;
                } else {
                    prev->next = current->next;
                }
                free(current);
            }
            free(newTerm);
            return;
        }
        prev = current;
        current = current->next;
    }
    
    // 插入新项
    if (prev == NULL) {
        newTerm->next = poly->head;
        poly->head = newTerm;
    } else {
        newTerm->next = current;
        prev->next = newTerm;
    }
    
    // 更新多项式的次数
    if (exp > poly->degree) {
        poly->degree = exp;
    }
}

// 多项式加法算法
Polynomial* addPolynomials(Polynomial* poly1, Polynomial* poly2) {
    Polynomial* result = createPolynomial();
    
    // 将第一个多项式的所有项添加到结果中
    Term* current = poly1->head;
    while (current != NULL) {
        addTerm(result, current->coefficient, current->exponent);
        current = current->next;
    }
    
    // 将第二个多项式的所有项添加到结果中
    current = poly2->head;
    while (current != NULL) {
        addTerm(result, current->coefficient, current->exponent);
        current = current->next;
    }
    
    return result;
}

// 打印多项式
void printPolynomial(Polynomial* poly) {
    if (poly->head == NULL) {
        printf("0");
        return;
    }
    
    Term* current = poly->head;
    int first = 1;
    
    while (current != NULL) {
        if (current->coefficient > 0 && !first) {
            printf(" + ");
        } else if (current->coefficient < 0) {
            if (first) {
                printf("-");
            } else {
                printf(" - ");
            }
        }
        
        if (current->exponent == 0) {
            printf("%d", abs(current->coefficient));
        } else if (current->exponent == 1) {
            if (abs(current->coefficient) == 1) {
                printf("x");
            } else {
                printf("%dx", abs(current->coefficient));
            }
        } else {
            if (abs(current->coefficient) == 1) {
                printf("x^%d", current->exponent);
            } else {
                printf("%dx^%d", abs(current->coefficient), current->exponent);
            }
        }
        
        first = 0;
        current = current->next;
    }
}

// 释放多项式内存
void freePolynomial(Polynomial* poly) {
    Term* current = poly->head;
    while (current != NULL) {
        Term* temp = current;
        current = current->next;
        free(temp);
    }
    free(poly);
}

// 主函数测试多项式加法算法
int main() {
    printf("=== 一元多项式加法算法测试 ===\n\n");
    
    // 创建第一个多项式: 3x^4 + 2x^3 - 5x^2 + 7x - 1
    Polynomial* poly1 = createPolynomial();
    addTerm(poly1, 3, 4);
    addTerm(poly1, 2, 3);
    addTerm(poly1, -5, 2);
    addTerm(poly1, 7, 1);
    addTerm(poly1, -1, 0);
    
    printf("多项式1: ");
    printPolynomial(poly1);
    printf("\n");
    
    // 创建第二个多项式: 2x^3 + 4x^2 - 3x + 5
    Polynomial* poly2 = createPolynomial();
    addTerm(poly2, 2, 3);
    addTerm(poly2, 4, 2);
    addTerm(poly2, -3, 1);
    addTerm(poly2, 5, 0);
    
    printf("多项式2: ");
    printPolynomial(poly2);
    printf("\n\n");
    
    // 执行加法运算
    Polynomial* result = addPolynomials(poly1, poly2);
    
    printf("加法结果: ");
    printPolynomial(result);
    printf("\n\n");
    
    // 测试用例2：包含相同指数的项
    printf("=== 测试用例2：合并同类项 ===\n");
    Polynomial* poly3 = createPolynomial();
    addTerm(poly3, 2, 3);
    addTerm(poly3, 3, 2);
    addTerm(poly3, 1, 1);
    
    Polynomial* poly4 = createPolynomial();
    addTerm(poly4, -2, 3);  // 与poly3中的2x^3相加后为0
    addTerm(poly4, 1, 2);
    addTerm(poly4, -1, 1);  // 与poly3中的x相加后为0
    
    printf("多项式3: ");
    printPolynomial(poly3);
    printf("\n");
    
    printf("多项式4: ");
    printPolynomial(poly4);
    printf("\n");
    
    Polynomial* result2 = addPolynomials(poly3, poly4);
    printf("加法结果: ");
    printPolynomial(result2);
    printf("\n\n");
    
    // 测试用例3：空多项式
    printf("=== 测试用例3：空多项式 ===\n");
    Polynomial* poly5 = createPolynomial();
    Polynomial* poly6 = createPolynomial();
    addTerm(poly6, 5, 2);
    addTerm(poly6, 3, 0);
    
    printf("多项式5 (空): ");
    printPolynomial(poly5);
    printf("\n");
    
    printf("多项式6: ");
    printPolynomial(poly6);
    printf("\n");
    
    Polynomial* result3 = addPolynomials(poly5, poly6);
    printf("加法结果: ");
    printPolynomial(result3);
    printf("\n");
    
    // 释放内存
    freePolynomial(poly1);
    freePolynomial(poly2);
    freePolynomial(result);
    freePolynomial(poly3);
    freePolynomial(poly4);
    freePolynomial(result2);
    freePolynomial(poly5);
    freePolynomial(poly6);
    freePolynomial(result3);
    
    return 0;
} 