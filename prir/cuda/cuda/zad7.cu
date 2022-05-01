#include<stdio.h>
#include <chrono>
#include <ctime>
#define N 32
#define M 8
#define MAX 8

__global__ void f(double* x, double* out)
{
	int index = threadIdx.x + blockIdx.x * blockDim.x;
	double xValue = 1.0 * x[index];
	out[index] = 1.0 / (1.0 + xValue * xValue);
}

void findXs(double* tab, double wym)
{
	double max = 1.0 * MAX;
	for (int i = 0; i < wym; i++)
		tab[i] = (1.0 * i) / ((wym - 1.0) * max);
}

void zeros(double* tab, int wym) {
	for (int i = 0; i < wym; i++)
		tab[i] = 0;
}

int main(void) {
	double *x, *d_x;
	double *out, *d_out;
	int size = (N + 1) * sizeof(double);
	cudaMalloc((void**)&d_x, size);
	cudaMalloc((void**)&d_out, size);

	auto start = std::chrono::system_clock::now();

	x = (double*)malloc(size);
	findXs(x, (double) 1.0 * (N + 1));

	out = (double*)malloc(size);
	zeros(x, N + 1);

	double max = 1.0 * MAX;
	double n = 1.0 * N;

	double dx = max / n;

	cudaMemcpy(d_x, x, size, cudaMemcpyHostToDevice);
	f << <N / M, M >> > (d_x, d_out);
	cudaMemcpy(out, d_out, size, cudaMemcpyDeviceToHost);

	double sum = 0;

	for (int i = 1; i < N; i++)
	{
		sum += out[i];
	}

	sum = 4 * dx * (sum + (out[0] + out[N + 1])/2.0);
	
	auto end = std::chrono::system_clock::now();
	std::chrono::duration<double> elapsed_seconds = end - start;

	printf("Out: %f | Time: %f", sum, elapsed_seconds.count());

	free(x); free(out);
	cudaFree(d_x); cudaFree(d_out);
	return 0;
}


