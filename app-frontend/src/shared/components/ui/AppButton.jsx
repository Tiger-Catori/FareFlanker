const AppButton = ({ href, children, ...rest }) => {
  return (
    <>
      <div className="btn__link">
        <a href={href} {...rest}>
          {children}
        </a>
      </div>
    </>
  );
};
export default AppButton;
