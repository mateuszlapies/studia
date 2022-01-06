import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import 'mock-local-storage';
import App from '../components/App';
import Offline from "../components/Offline";
import Loading from "../components/Loading";
import Body from "../components/Body";

test('logo_check', () => {
  render(<App />);
  const logoElement = screen.getByText("Cards Against Humanity");
  expect(logoElement.className).toBe("navbar-brand");
});

test('login_check', () => {
  render(<App />);
  const loginElement = screen.getByText("login");
  expect(loginElement).toBeInTheDocument();
});

test('options_check', () => {
  render(<App />);
  const loginElements = screen.getAllByText("Login");
  const registerElements = screen.getAllByText("Register");
  const userElements = screen.getAllByText("User");
  const passElements = screen.getAllByText("Password");
  expect(loginElements).toHaveLength(3);
  expect(registerElements).toHaveLength(3);
  expect(userElements).toHaveLength(2);
  expect(passElements).toHaveLength(2);
});

test('offline_check', () => {
  render(<Offline />);
  const offline = screen.getByText("Service is unfortunately currently offline");
  const offlineIcon = screen.getByTestId("offline-icon");
  expect(offline).toBeInTheDocument();
  expect(offlineIcon).toBeInTheDocument();
});

test('loading', () => {
  render(<Loading />);
  const loading = screen.getByText("Loading...");
  expect(loading).toBeInTheDocument();
});

test('body_check', () => {
  render(<Body>Test</Body>);
  const body = screen.getByText("Test");
  expect(body).toBeInTheDocument();
});