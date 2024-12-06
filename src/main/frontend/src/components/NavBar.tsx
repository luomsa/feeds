import { Anchor, Button, Flex } from "@mantine/core";
import { Link } from "react-router";
import LoginModal from "./LoginModal.tsx";
import { useAuth } from "../hooks/useAuth.tsx";
import { useDisclosure } from "@mantine/hooks";
import client from "../lib/api/client.ts";

const NavBar = () => {
  const { state } = useAuth();
  const [opened, { open, close }] = useDisclosure(false);
  const { dispatch } = useAuth();
  const handleLogout = async () => {
    await client.POST("/api/auth/logout");
    dispatch({ type: "RESET_USER" });
  };
  return (
    <Flex justify={"space-between"} my={"sm"} align={"center"}>
      <Anchor style={{ color: "black" }} fz={"h2"} component={Link} to={"/"}>
        Feeds
      </Anchor>
      <Flex gap={"md"}>
        {!state.user && <Button onClick={open}>Login</Button>}
        {state.user && (
          <>
            {state.user.username} <Button onClick={handleLogout}>Logout</Button>
          </>
        )}
      </Flex>
      <LoginModal opened={opened} close={close} />
    </Flex>
  );
};
export default NavBar;
