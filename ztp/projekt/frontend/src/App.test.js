import { render, screen } from '@testing-library/react';
import App from './App';

test('dumb_test', () => {
  render(<App />);
  const linkElement = screen.getByText("CAH");
  expect(linkElement).toBeInTheDocument();
});
