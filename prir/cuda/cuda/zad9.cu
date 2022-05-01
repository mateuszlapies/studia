#include<stdio.h>

__global__ void action(double* x, double* out) {
	int index = threadIdx.x + blockIdx.x * blockDim.x;
	double top = 1;
	for (int i = 0; i < index; i++) {
		top *= -1.0;
	}
	double bottom = 1;
	for (double s = 2 * index + 1; s > 1; s--) {
		bottom *= s;
	}

	double bottom_power = 1;
	for (int i = 0; i < 2 * index + 1; i++) {
		bottom_power *= *x;
	}

	out[index] = top / bottom *  bottom_power;
}

int main(void)
{
	double N = 3;
	double *x, *d_x;
	
	cudaMalloc((void**)&d_x, sizeof(double));
	x = (double*)malloc(sizeof(double));
	*x = 0.25;

	double *out, *d_out;
	int size = N * sizeof(double);
	cudaMalloc((void**)&d_out, size);
	out = (double*)malloc(size);

	cudaMemcpy(d_x, x, sizeof(double), cudaMemcpyHostToDevice);
	action <<<N, 8>>> (d_x, d_out);
	cudaMemcpy(out, d_out, size, cudaMemcpyDeviceToHost);

	double sum = 0;

	for (int i = 0; i < N; i++) {
		sum += out[i];
	}

	printf("Out: %f", sum);
	free(x); free(out);
	cudaFree(d_x); cudaFree(d_out);
	return 0;
}