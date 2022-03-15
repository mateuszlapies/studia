#include <omp.h>
#include <stdio.h>

void strategy_a(int iterations) {
    int i;
    #pragma omp parallel for schedule(static,3)
    for(i=0;i<iterations;i++){ printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

void strategy_b(int iterations) {
    int i;
    #pragma omp parallel for schedule(static)
    for(i=0;i<iterations;i++){ printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

void strategy_c(int iterations) {
    int i;
    #pragma omp parallel for schedule(dynamic,3)
    for(i=0;i<iterations;i++){ printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

void strategy_d(int iterations) {
    int i;
    #pragma omp parallel for schedule(dynamic)
    for(i=0;i<(iterations);i++){ printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

int main() {
    int iterations = 15;
    omp_set_num_threads(4);
    strategy_a(iterations);
    strategy_b(iterations);
    strategy_c(iterations);
    strategy_d(iterations);
}
