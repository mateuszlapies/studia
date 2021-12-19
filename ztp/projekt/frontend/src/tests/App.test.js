import { render, screen } from '@testing-library/react';
import App from '../components/App';

test('dumb_test', () => {
  render(<App />);
  const linkElement = screen.getByText("CAH");
  expect(linkElement).toBeInTheDocument();
});
