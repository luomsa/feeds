import { Container } from "@mantine/core";
import { Outlet } from "react-router";

function App() {
  return (
    <Container>
      <Outlet />
    </Container>
  );
}

export default App;
