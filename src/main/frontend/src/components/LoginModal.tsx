import { Modal } from "@mantine/core";
import { useState } from "react";
import LoginForm from "./LoginForm.tsx";
import RegisterForm from "./RegisterForm.tsx";
enum AuthType {
  LOGIN,
  REGISTER,
}
type Props = {
  opened: boolean;
  close: () => void;
};
const LoginModal = ({ opened, close }: Props) => {
  const [authType, setAuthType] = useState(AuthType.LOGIN);

  return (
    <>
      <Modal opened={opened} onClose={close}>
        {authType == AuthType.LOGIN ? (
          <LoginForm
            close={close}
            handleAuthType={() => setAuthType(AuthType.REGISTER)}
          />
        ) : (
          <RegisterForm handleAuthType={() => setAuthType(AuthType.LOGIN)} />
        )}
      </Modal>
    </>
  );
};
export default LoginModal;
