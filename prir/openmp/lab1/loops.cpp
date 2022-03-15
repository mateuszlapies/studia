#include <omp.h>
#include <stdio.h>
#include <math.h>

void strategy_non_reduction(int iterations) {
    int i; double sum = 0;
    #pragma omp parallel for
    for(i=0;i<iterations;i++){ sum += pow(2,2); }
}

void strategy_reduction(int iterations) {
    int i; double sum = 0;
    #pragma omp parallel for reduction(+:sum)
    for (i = 0; i < iterations; i++) { sum += pow(2, 2); }
}

int main() {
    int iterations = 5000;
    omp_set_num_threads(4);
    double start_time = omp_get_wtime();
    strategy_non_reduction(iterations);
    printf("a: %f\n", omp_get_wtime() - start_time);
    start_time = omp_get_wtime();
    strategy_reduction(iterations);
    printf("b: %f\n", omp_get_wtime() - start_time);
}
