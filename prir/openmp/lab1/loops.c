#include <omp.h>
#include <stdio.h>

void strategy_a(int iterations) {
    int i; int sum = 0;
    #pragma omp parallel for schedule(static,3)
    for(i=0;i<iterations;i++){ sum++; }
}

void strategy_b(int iterations) {
    int i; int sum = 0;
    #pragma omp parallel for schedule(static)
    for(i=0;i<iterations;i++){ sum++; }
}

void strategy_c(int iterations) {
    int i; int sum = 0;
    #pragma omp parallel for schedule(dynamic,3)
    for(i=0;i<iterations;i++){ sum++; }
}

void strategy_d(int iterations) {
    int i; int sum = 0;
    #pragma omp parallel for schedule(dynamic)
    for(i=0;i<(iterations);i++){ sum++; }
}

int main() {
    int iterations = 150000;
    omp_set_num_threads(4);
    double start_time = omp_get_wtime();
    strategy_a(iterations);
    printf("a: %f\n", omp_get_wtime() - start_time);
    start_time = omp_get_wtime();
    strategy_b(iterations);
    printf("b: %f\n", omp_get_wtime() - start_time);
    start_time = omp_get_wtime();
    strategy_c(iterations);
    printf("c: %f\n", omp_get_wtime() - start_time);
    start_time = omp_get_wtime();
    strategy_d(iterations);
    printf("d: %f\n", omp_get_wtime() - start_time);
}
