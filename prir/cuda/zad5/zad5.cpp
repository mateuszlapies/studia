#include<stdio.h>
#include <chrono>
#include <ctime>
#define N 32
#define M 8
#define MAX 16

double f(double x)
{
	return 1.0 / (1.0 + x * x);
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
	double* x;
	x = new double[N + 1];

	auto start = std::chrono::system_clock::now();

	findXs(x, (double)1.0 * (N + 1));

	double max = 1.0 * MAX;
	double n = 1.0 * N;

	double dx = max / n;

	double sum = 0;

	for (int i = 1; i < N; i++)
	{
		sum += f(x[i]);
	}

	sum = 4 * dx * (sum + (f(x[0]) + f(x[N + 1])) / 2.0);

	auto end = std::chrono::system_clock::now();
	std::chrono::duration<double> elapsed_seconds = end - start;

	printf("Out: %f | Time: %f", sum, elapsed_seconds.count());
	return 0;
}

