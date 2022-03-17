#include <omp.h>
#include <stdio.h>

//strategia a - static, rozmiar porcji = 3
void strategy_a(int iterations) {
    int i;
    #pragma omp parallel for schedule(static,3)
    for(i=0;i<iterations;i++){ int a = 0; a += 1;//printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

//strategia b - static, domyślny rozmiar porcji
void strategy_b(int iterations) {
    int i;
    #pragma omp parallel for schedule(static)
    for(i=0;i<iterations;i++){ int a = 0; a += 1;//printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

//strategia c - dynamic, rozmiar porcji = 3
void strategy_c(int iterations) {
    int i;
    #pragma omp parallel for schedule(dynamic,3)
    for(i=0;i<iterations;i++){ int a = 0; a += 1;//printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

//strategia d - static, domyślny rozmiar porcji
void strategy_d(int iterations) {
    int i;
    #pragma omp parallel for schedule(dynamic)
    for(i=0;i<(iterations);i++){ int a = 0; a += 1;//printf("thread %d, index %d\n",omp_get_thread_num(),i); }
}

int main() {
	//15000 iteracji
    int iterations = 15000;
	//ustawienie 4 dostępnych wątków dla programu
    omp_set_num_threads(4);
	double start_time = omp_get_wtime();
    strategy_a(iterations);
	//zwrócenie do konsoli osiągniętego czasu przez strategię
    printf("a: %f\n", omp_get_wtime() - start_time);
	start_time = omp_get_wtime();
    strategy_b(iterations);
	printf("a: %f\n", omp_get_wtime() - start_time);
	start_time = omp_get_wtime();
    strategy_c(iterations);
	printf("a: %f\n", omp_get_wtime() - start_time);
	start_time = omp_get_wtime();
    strategy_d(iterations);
	printf("a: %f\n", omp_get_wtime() - start_time);
}