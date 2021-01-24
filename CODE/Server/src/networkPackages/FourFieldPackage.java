package networkPackages;

    public class FourFieldPackage extends NetworkPackage
    {
        private String firstField;
        private String secondField;
        private String thirdField;
        private String fourthField;

        public FourFieldPackage(NetworkType type, String firstField, String secondField, String thirdField, String fourthField)
        {
            super(type);
            this.firstField = firstField;
            this.secondField = secondField;
            this.thirdField = thirdField;
            this.fourthField = fourthField;
        }

        public String getFirstField()
        {
            return firstField;
        }

        public String getSecondField()
        {
            return secondField;
        }

        public String getThirdField()
        {
            return thirdField;
        }

        public String toString()
        {
            return getType().toString();
        }

        public String getFourthField()
        {
            return fourthField;
        }
    }



