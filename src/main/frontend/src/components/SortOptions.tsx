import { Anchor, Flex } from "@mantine/core";
import { Link } from "react-router";

const SortOptions = () => {
  return (
    <Flex gap={"md"}>
      <Anchor component={Link} to="?sort=latest">
        Latest
      </Anchor>
      <Anchor component={Link} to="?sort=comments">
        Comments
      </Anchor>
    </Flex>
  );
};
export default SortOptions;
