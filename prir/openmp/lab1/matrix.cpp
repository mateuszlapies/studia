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
    }
    for(int i = 0; i < M; i++) {
        for(int j = 0; j < P; j++) {
            multi[i][j] = rand()%10;
        }
    }
    int i,j,k;
    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            int sum = 0;
            for(k = 0; k < M; k++) {
                sum += main[i][k] * multi[k][j];
            }
            output1[i][j] = sum;
        }
    }
    double time = omp_get_wtime();
    for(i = 0; i < N; i++) {
    #pragma omp parallel for private(j) private(k) schedule(dynamic, 3)
        for(j = 0; j < P; j++) {
            int sum = 0;
            for(k = 0; k < M; k++) {
                sum += main[i][k] * multi[k][j];
            }
            output2[i][j] = sum;
        }
    }
    printf("time: %f\n\n", omp_get_wtime() - time);

    bool correct = true;
    int errors = 0;
    for(i = 0; i < N; i++) {
        for(j = 0; j < P; j++) {
            if(output1[i][j] != output2[i][j]) {
                correct = false;
                errors++;
            }
        }
    }
    printf("is correct? %s | %i errors", correct ? "OK" : "NOT OK", errors);
}
