import { Anchor, Button, Flex } from "@mantine/core";
import { Link } from "react-router";
import { useAuth } from "../hooks/useAuth.tsx";

const SortOptions = () => {
  const { state } = useAuth();
  return (
    <Flex gap={"md"} my={"sm"}>
      <Anchor style={{ color: "black" }} component={Link} to="?sort=latest">
        Latest
      </Anchor>
      <Anchor style={{ color: "black" }} component={Link} to="?sort=comments">
        Comments
      </Anchor>
      {state.user && <Button ms={"auto"}>New post</Button>}
    </Flex>
  );
};
export default SortOptions;
