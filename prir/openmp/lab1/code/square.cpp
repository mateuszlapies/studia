#include <omp.h>
#include <stdio.h>
#include <math.h>


//procedura dodająca kwadrat liczby dwa bez redukcji
void strategy_non_reduction(int iterations) {
    int i; double sum = 0;
    #pragma omp parallel for
    for(i=0;i<iterations;i++){ sum += pow(2,2); }
}

//procedura dodająca kwadrat liczby dwa z redukcją
void strategy_reduction(int iterations) {
    int i; double sum = 0;
    #pragma omp parallel for reduction(+:sum)
    for (i = 0; i < iterations; i++) { sum += pow(2, 2); }
}

int main() {
	//5000 iteracji
    int iterations = 5000;
	//ustawienie 4 dostępnych wątków dla programu
    omp_set_num_threads(4);
	//pobranie początkowej wartości czasu
    double start_time = omp_get_wtime();
    strategy_non_reduction(iterations);
	//zwrócenie do konsoli osiągniętego czasu przez strategię
    printf("a: %f\n", omp_get_wtime() - start_time);
    start_time = omp_get_wtime();
    strategy_reduction(iterations);
    printf("b: %f\n", omp_get_wtime() - start_time);
}
