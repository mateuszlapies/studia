#include <omp.h>
#include <ctime>
#include <cstdlib>
#include <cstdio>

int main() {
    omp_set_num_threads(4);
    srand(time(NULL));

    int N = 10;
    int M = 8;
    int P = 4;

    printf("N: %i, M: %i, P: %i\n\n", N, M, P);

    int main [N][M];
    int multi [M][P];
    int output1 [N][P];
    int output2 [N][P];

    for(int i = 0; i < N; i++) {
        for(int j = 0; j < M; j++) {
            main[i][j] = rand()%10;
        }
        for(int p = 0; p < P; p++) {
            output1[i][p] = 0;
            output2[i][p] = 0;
        }
    }
    for(int i = 0; i < M; i++) {
        for(int j = 0; j < P; j++) {
            multi[i][j] = rand()%10;
        }
    }
    int i,j,k;
    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            for(k = 0; k < M; k++) {
                output1[i][j] += main[i][k] * multi[k][j];
            }
        }
    }
    double time = omp_get_wtime();
    #pragma omp parallel for private(i) schedule(dynamic)
    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            for(k = 0; k < M; k++) {
                output2[i][j] += main[i][k] * multi[k][j];
            }
        }
    }
    printf("time: %f\n\n", omp_get_wtime() - time);

    bool correct = true;
    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            if(output1[i][j] != output2[i][j])
                correct = false;
        }
    }
    printf("is correct? %s", correct ? "OK" : "NOT OK");
}
