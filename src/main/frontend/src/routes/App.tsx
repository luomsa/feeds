import { Outlet } from "react-router";
import NavBar from "../components/NavBar.tsx";
import { Container } from "@mantine/core";

function App() {
  return (
    <Container>
      <NavBar />
      <Outlet />
    </Container>
  );
}

export default App;
