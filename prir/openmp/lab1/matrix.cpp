#include <omp.h>
#include <ctime>
#include <cstdlib>
#include <cstdio>

int main() {
    omp_set_num_threads(4);
    srand(time(NULL));

    int N = 4;
    int M = 3;
    int P = 3;

    int main [N][M];
    int multi [M][P];
    int output [N][P];

    for(int i = 0; i < N; i++) {
        for(int j = 0; j < M; j++) {
            main[i][j] = rand()%10;
            printf("%i\t", main[i][j]);
        }
        for(int p = 0; p < P; p++) {
            output[i][p] = 0;
        }
        printf("\n");
    }
    printf("\n");
    for(int i = 0; i < M; i++) {
        for(int j = 0; j < P; j++) {
            multi[i][j] = rand()%10;
            printf("%i\t", multi[i][j]);
        }
        printf("\n");
    }
    printf("\n");
    double time = omp_get_wtime();
    int i,j,k;
    #pragma omp parallel for private(i)
    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            for(k = 0; k < M; k++) {
                output[i][j] += main[i][k] * multi[k][j];
            }
        }
    }
    printf("time: %f\n\n", omp_get_wtime() - time);

    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            printf("%i\t", output[i][j]);
        }
        printf("\n");
    }
}
