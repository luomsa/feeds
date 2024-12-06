import { Anchor, Box, Container, Title } from "@mantine/core";
import { Link, useRouteError } from "react-router";

const ErrorBoundary = () => {
  const error = useRouteError() as { statusText?: string };
  console.log("error boundary: ", error);
  return (
    <Container>
      <Box>
        <Anchor style={{ color: "black" }} fz={"h1"} component={Link} to={"/"}>
          Feeds
        </Anchor>
        <Title style={{ textAlign: "center" }}>
          {error?.statusText ?? "Error"}
        </Title>
      </Box>
    </Container>
  );
};
export default ErrorBoundary;
