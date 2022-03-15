#include <omp.h>
#include <ctime>
#include <cstdlib>
#include <cstdio>

int main() {
    omp_set_num_threads(4);
    srand(time(NULL));

    int N = 400;
    int M = 300;
    int P = 300;

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
    int row;
    #pragma omp parallel for private(row)
    for (row = 0; row < N; row++) {
        for(int column = 0; column < P; column++) {
            for(int m = 0; m < M; m++) {
                output[row][column] += main[row][m] * multi[m][column];
            }
            printf("%i\t", output[row][column]);
        }
        printf("\n");
    }
    printf("time: %f\n", omp_get_wtime() - time);
}
