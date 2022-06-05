import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class booking_program {
    private static LocalDate now = LocalDate.now();
    static int year_today = now.getYear();
    static int month_today = now.getMonthValue();
    static int day_today = now.getDayOfMonth();
    public static void main(String[] args) throws IOException {
        System.out.println("<<스터디 공간 예약 프로그램입니다>>");
        home();
    }
    private static void home() throws IOException {
        System.out.println();
        System.out.println("입장할 모드를 선택해주세요.");
        System.out.println("1)관리자 모드\t\t2)사용자 모드\t\t3)프로그램 종료");
        char mode = checkInput(0);
        if (mode=='1'){
            Administrator administrator = new Administrator();
        } else if (mode=='2') {
            User user = new User();
        } else if (mode=='3' || mode=='q') {
            new Exit();
        }
    }
    private static class Administrator{
        public Administrator() throws IOException { //로그인 과정
            System.out.println("---------------------------");
            System.out.println("- 관리자 모드로 로그인합니다. -");
            System.out.println("---------------------------");
            new branch_management();
            home();
        }

        /*
         * 스터디 지점 관리 클래스. (맨 처음에 들어가면 지점 상태를 보여주도록 하자)
         * 스터디 지점은 1번~6번까지 존재할 수 있음.
         * 스터디 지점을 생성한다고 해도 스터디 공간 안 생김. 알아서 공간을 추가해줘야 함.
         * 지점 내에 스터디 공간이 생성되어 있을 경우 지점 삭제 불가.
         */
        class branch_management{
            public branch_management() throws IOException {
                while (true) { //로그인 상태 유지
                    System.out.println("\t" + LocalDate.now() + " 지점 상태");
                    read();
                    System.out.println();
                    System.out.println("1)지점 정보 수정\t\t2)지점 추가\t\t3)지점 삭제");
                    System.out.println("(로그아웃하고 초기 화면으로 돌아가시려면 q를 입력해주세요)");

                    char mode = checkInput(1);
                    if (mode == '1') {
                        char branch = '0';
                        String[] range = new String[]{"1", "2", "3", "4", "5", "6"};
                        while (branch == '0') {
                            //System.out.println();
                            System.out.print("[지점수정]어느 지점으로 이동할까요? >> ");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                            if (str.length() != 1) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            if (!Arrays.asList(range).contains(str)) {
                                System.out.printf("해당 지점 번호는 사용할 수 없습니다. 1~6 사이의 자연수를 입력해주세요.\n");
                                continue;
                            }
                            if (!checkBranch(str)) {
                                System.out.printf("%s번 지점은 존재하지 않습니다.\n", str);
                                System.out.printf("※스터디 공간 수정을 원하시면 지점 생성 후 다시 시도해주세요.\n");
                                continue;
                            }
                            branch = str.charAt(0);
                        }
                        modify(branch);
                    } else if (mode == '2') {
                        char branch = '0';
                        String[] range = new String[]{"1", "2", "3", "4", "5", "6"};
                        while (branch == '0') {
                            //System.out.println();
                            System.out.print("[지점추가]추가할 지점의 번호를 입력해주세요 >> ");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                            if (str.length() != 1) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            if (!Arrays.asList(range).contains(str)) {
                                System.out.printf("해당 지점 번호는 사용할 수 없습니다. 1~6 사이의 자연수를 입력해주세요.\n");
                                continue;
                            }
                            if (checkBranch(str)) {
                                System.out.printf("%s번 지점은 이미 존재합니다.\n", str);
                                System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                continue;
                            }
                            branch = str.charAt(0);
                        }
                        add(branch);
                    } else if (mode == '3') {
                        char branch = '0';
                        String[] range = new String[]{"1", "2", "3", "4", "5", "6"};
                        while (branch == '0') {
                            //System.out.println();
                            System.out.print("[지점삭제]삭제할 지점의 번호를 입력해주세요 >> ");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                            if (str.length() != 1) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            if (!Arrays.asList(range).contains(str)) {
                                System.out.printf("해당 지점 번호는 사용할 수 없습니다. 1~6 사이의 자연수를 입력해주세요.\n");
                                continue;
                            }
                            if (!checkBranch(str)) {
                                System.out.printf("%s번 지점은 존재하지 않습니다.\n", str);
                                System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                continue;
                            }
                            if (!can_delete(str)) {
                                System.out.printf("%s번 지점에 스터디 공간이 존재합니다.\n", str);
                                System.out.printf("※스터디 공간을 모두 삭제한 뒤 다시 시도해주세요.\n");
                                System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                continue;
                            }
                            branch = str.charAt(0);
                        }
                        delete(branch);
                        //
                    } else if (mode == 'q') q(); //홈 화면에서 quit명령 실행 시 초기화면으로 돌아감.
                }
            }

            //스터디 지점 상태 정보 저장해놓은 파일 불러와 읽음
            void read() throws IOException {
                String filePath = "src/Administrator_info.txt";
                File file = new File(filePath); // File 객체 생성
                if (!file.exists()) {           // 파일이 존재하지 않으면
                    file.createNewFile();       // 신규생성
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                    writer.write("1.XXXXX\n");
                    writer.write("2.XXXXX\n");
                    writer.write("3.XXXXX\n");
                    writer.write("4.XXXXX\n");
                    writer.write("5.XXXXX\n");
                    writer.write("6.XXXXX");
                    writer.close();
                }
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parsed = line.split("[.]");
                    if (parsed[1].equals("XXXXX")) {
                        System.out.printf("%s : 지점이 존재하지 않습니다.\n", parsed[0]);
                    } else if (parsed[1].equals("00000")) {
                        System.out.printf("%s : 스터디 공간을 생성해주세요.\n", parsed[0]);
                    } else {
                            System.out.printf("%s : ", parsed[0]);
                            for (int i = 0; i < parsed[1].length(); i++) {
                                if (!parsed[1].substring(i, i + 1).equals("X") && !parsed[1].substring(i, i + 1).equals("0")) {
                                    System.out.printf("%d호(%d인실) ", i + 1, Integer.parseInt(parsed[1].substring(i, i + 1), 16));
                                }
                            }

                        System.out.printf("\n");
                    }
                }
                reader.close();
            }

            //고유한 지점 번호를 입력 받아 수행
            void add(char branch) throws IOException {
                String filePath = "src/Administrator_info.txt";
                String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
                //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

                //Files.delete(Paths.get(filePath));
                StringBuffer stringBuffer = new StringBuffer(contents);
                int index = contents.indexOf(branch+".")+2;
                stringBuffer.replace(index,index+5, "00000");
                contents = stringBuffer.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(contents);
                writer.close();
                //Files.write(Files.createFile(Paths.get(filePath)), contents.getBytes());
                System.out.printf("지점 추가가 완료되었습니다.\n");
            }

            void delete(char branch) throws IOException {
                String filePath = "src/Administrator_info.txt";
                String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
                //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

                //Files.delete(Paths.get(filePath));
                StringBuffer stringBuffer = new StringBuffer(contents);
                int index = contents.indexOf(branch+".")+2;
                stringBuffer.replace(index,index+5, "XXXXX");
                contents = stringBuffer.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(contents);
                writer.close();
                //Files.write(Files.createFile(Paths.get(filePath)), contents.getBytes());
                System.out.printf("지점 삭제가 완료되었습니다.\n");
            }

            //스터디 공간 관리 클래스로 전환
            void modify(char branch) throws IOException {
                new study_space_management(branch);
            }
        }

        static boolean checkBranch(String str) throws IOException { //지점이 존재하면 true, 존재하지 않으면 false return
            String filePath = "src/Administrator_info.txt";
            File file = new File(filePath);
            BufferedReader file_reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = file_reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                if (parsed[0].equals(str)) {
                    if (parsed[1].equals("XXXXX")) return false;
                }
            }
            return true;
        }

        private boolean can_delete(String str) throws IOException { //삭제할 수 있으면 true, 삭제할 수 없으면 false return
            String filePath = "src/Administrator_info.txt";
            File file = new File(filePath);
            BufferedReader file_reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = file_reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                if (parsed[0].equals(str)) {
                    if (parsed[1].equals("00000")) return true;
                }
            }
            return false;
        }

        /*
         * 스터디 공간 관리 클래스.
         * 스터디 공간은 1호실~5호실까지 존재할 수 있음.
         * 호실 생성 시 몇인실인지 입력해줘야 함. 1인실~10인실까지 존재할 수 있음.
         * 예약이 잡혀있을 경우 스터디 공간 삭제 불가
         */
        class study_space_management{
            public study_space_management(char branch) throws IOException {
                System.out.println();
                System.out.printf("지점[%c번] 정보를 가져옵니다...\n", branch);
                System.out.println("-----------------------------------------");
                System.out.println("------------스터디 공간 상태 정보-----------");
                String space_info = read(branch);
                boolean is_there_any_room = false;
                boolean is_it_full = true;
                for (int i=0; i<space_info.length();i++){
                    if(checkSpace(space_info, Integer.toString(i+1))) is_there_any_room = true;
                    else is_it_full = false;
                }

                while (true) {
                    System.out.println("1)스터디 공간 정보 수정\t\t2)스터디 공간 추가\t\t3)스터디 공간 삭제");
                    System.out.println("(모드를 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)");
                    char mode = checkInput(1);
                    if (mode == '1') {
                        if (is_there_any_room) {
                            String space = "0";
                            String people = "0";
                            String[] range = new String[]{"1", "2", "3", "4", "5"};
                            String[] range2 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

                            System.out.print("1~5호실 중 수정을 원하는 호실을 입력해주세요.\n");
                            while (space == "0") {
                                //System.out.println();
                                System.out.print("[공간수정]몇호실 정보를 수정할까요? >> ");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                                if (str.length() != 1) continue;
                                if (str.equals("q")) {
                                    System.out.printf("입력을 중단합니다...\n");
                                    break; //작성을 종료하고 다시 mode를 입력받음.
                                }
                                if (!Arrays.asList(range).contains(str)) {
                                    System.out.printf("해당 방 번호는 사용할 수 없습니다. 1~5 사이의 자연수를 입력해주세요.\n", str);
                                    continue;
                                }
                                if (!checkSpace(space_info, str)) {
                                    System.out.printf("%s호실은 존재하지 않습니다.\n", str);
                                    System.out.printf("※%s호실 인원 수정을 원하시면 호실 생성 후 다시 시도해주세요.\n", str);
                                    continue;
                                }
                                space = str;
                            }
                            while (people == "0") {
                                System.out.printf("[공간수정]%s호실 최대 허용 인원수를 입력해주세요 >> ", space);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String str = reader.readLine();

                                if (str.equals("q")) {
                                    System.out.printf("입력을 중단합니다...\n");
                                    break; //작성을 종료하고 다시 mode를 입력받음.
                                }
                                if (!Arrays.asList(range2).contains(str)) {
                                    System.out.printf("허용 인원수는 최대 10인까지입니다. 1~10 사이의 자연수를 입력해주세요.\n", str);
                                    System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                    continue;
                                }
                                people = Integer.toHexString(Integer.parseInt(str));
                            }
                            modify(branch, space, people);
                            break;
                        } else {
                            System.out.print("방이 존재하지 않습니다.\n");
                            System.out.printf("※공간 수정을 원하시면 호실 생성 후 다시 시도해주세요.\n");
                        }
                    } else if (mode == '2') {
                        if (!is_it_full) {
                            String space = "0";
                            String people = "0";
                            String[] range = new String[]{"1", "2", "3", "4", "5"};
                            String[] range2 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
                            while (space == "0") {
                                //System.out.println();
                                System.out.print("[공간추가]추가할 방의 번호를 입력해주세요 >> ");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                                if (str.length() != 1) continue;
                                if (str.equals("q")) {
                                    System.out.printf("입력을 중단합니다...\n");
                                    break; //작성을 종료하고 다시 mode를 입력받음.
                                }
                                if (!Arrays.asList(range).contains(str)) {
                                    System.out.printf("해당 방 번호는 사용할 수 없습니다. 1~5 사이의 자연수를 입력해주세요.\n", str);
                                    continue;
                                }
                                if (checkSpace(space_info, str)) {
                                    System.out.printf("%s호실은 이미 존재합니다.\n", str);
                                    System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                    continue;
                                }
                                space = str;
                            }
                            while (people == "0") {
                                System.out.printf("[공간추가]%s호실 최대 허용 인원수를 입력해주세요 >> ", space);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String str = reader.readLine();

                                if (str.equals("q")) {
                                    System.out.printf("입력을 중단합니다...\n");
                                    break; //작성을 종료하고 다시 mode를 입력받음.
                                }
                                if (!Arrays.asList(range2).contains(str)) {
                                    System.out.printf("허용 인원수는 최대 10인까지입니다. 1~10 사이의 자연수를 입력해주세요.\n", str);
                                    System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                    continue;
                                }
                                people = Integer.toHexString(Integer.parseInt(str));
                            }
                            add(branch, space, people);
                            break;
                        } else {
                            System.out.print("방이 모두 찼습니다.\n");
                            System.out.printf("※공간 추가를 원하시면 호실 삭제 후 다시 시도해주세요.\n");
                        }
                    } else if (mode == '3') {
                        if (is_there_any_room) {
                            String space = "0";
                            String[] range = new String[]{"1", "2", "3", "4", "5"};
                            while (space == "0") {
                                //System.out.println();
                                System.out.print("[공간삭제]삭제할 호실의 번호를 입력해주세요 >> ");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                                if (str.length() != 1) continue;
                                if (str.equals("q")) {
                                    System.out.printf("입력을 중단합니다...\n");
                                    break; //작성을 종료하고 다시 mode를 입력받음.
                                }
                                if (!Arrays.asList(range).contains(str)) {
                                    System.out.printf("해당 방 번호는 사용할 수 없습니다. 1~5 사이의 자연수를 입력해주세요.\n", str);
                                    continue;
                                }
                                if (!checkSpace(space_info, str)) {
                                    System.out.printf("%s호실은 존재하지 않습니다.\n", str);
                                    System.out.printf("(입력을 중단하고 홈 화면으로 돌아가시려면 q를 입력해주세요)\n");
                                    continue;
                                }
                                space = str;
                            }
                            delete(branch, space);
                            break;
                        } else {
                            System.out.print("방이 존재하지 않습니다.\n");
                            System.out.printf("※공간 삭제를 원하시면 호실 생성 후 다시 시도해주세요.\n");
                        }
                    } else if (mode == 'q') break;
                }
                System.out.printf("초기 화면으로 돌아갑니다..\n");
            }

            private boolean checkSpace(String space_info, String room_number) {
                //스터디 공간이 있으면 true, 없으면 false return
                //space)info[room_number-1]위치의 정보에 0이 아닌 다른 숫자가 있다면 이미 방이 있는 것.
                if (space_info.charAt(Integer.parseInt(room_number)-1)=='0') return false;
                else return true;
            }

            //스터디 공간 상태 정보 저장해놓은 파일 불러와 읽음
            static String read(char branch) throws IOException {
                String filePath = "src/Administrator_info.txt";
                File file = new File(filePath); // File 객체 생성
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                boolean is_there_any_room = false;
                String result = null;
                while ((line = reader.readLine()) != null) {
                    String[] parsed = line.split("[.]");
                    if (parsed[0].equals(Character.toString(branch))) {
                        result = parsed[1];
                        break;
                    }
                }
                System.out.printf("호실 번호\t최대 허용 인원\t\n");

                for (int i=0; i<result.length(); i++) {
                    if (result.charAt(i) != '0') {
                        System.out.printf("%s호\t\t\t%d인\n", i+1, Integer.parseInt(Character.toString(result.charAt(i)), 16));
                        is_there_any_room = true;
                    }
                }
                if (!is_there_any_room) System.out.printf("※방이 존재하지 않습니다");
                System.out.printf("\n");
                return result;
            }

            //고유한 지점 번호를 입력 받아 수행
            void add(char branch, String room_number, String people) throws IOException {
                String filePath = "src/Administrator_info.txt";
                String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
                //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

                //Files.delete(Paths.get(filePath));
                StringBuffer stringBuffer = new StringBuffer(contents);
                int index = contents.indexOf(branch+".")+2+Integer.parseInt(room_number)-1;
                stringBuffer.replace(index,index+1, people);
                contents = stringBuffer.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(contents);
                writer.close();

                System.out.printf("스터디 공간 추가가 완료되었습니다.\n");
            }

            void delete(char branch, String room_number) throws IOException {
                String filePath = "src/Administrator_info.txt";
                String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
                //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

                //Files.delete(Paths.get(filePath));
                StringBuffer stringBuffer = new StringBuffer(contents);
                int index = contents.indexOf(branch+".")+2+Integer.parseInt(room_number)-1;
                stringBuffer.replace(index,index+1, "0");
                contents = stringBuffer.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(contents);
                writer.close();
                //Files.write(Files.createFile(Paths.get(filePath)), contents.getBytes());
                System.out.printf("스터디 공간 삭제가 완료되었습니다.\n");
            }

            //스터디 공간 기타 속성들 수정. 처음 생성 시에는 default 정보가 들어가 있음.
            void modify(char branch, String room_number, String people) throws IOException {
                String filePath = "src/Administrator_info.txt";
                String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
                //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

                //Files.delete(Paths.get(filePath));
                StringBuffer stringBuffer = new StringBuffer(contents);
                int index = contents.indexOf(branch+".")+2+Integer.parseInt(room_number);
                stringBuffer.replace(index,index+1, people);
                contents = stringBuffer.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                writer.write(contents);
                writer.close();
                //Files.write(Files.createFile(Paths.get(filePath)), contents.getBytes());
                System.out.printf("스터디 공간 수정이 완료되었습니다.\n");

            }

            void create_space_file(char branch, String room_number, String people) throws IOException {
                String filePath = "src/"+branch+"_"+room_number+".txt"; // ex) src/3_1.txt
                File file = new File(filePath); // File 객체 생성
                if (!file.exists()) {           // 파일이 존재하지 않으면
                    file.createNewFile();       // 신규생성
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                    writer.write("1.XXXXX\n");
                    writer.write("2.XXXXX\n");
                    writer.write("3.XXXXX\n");
                    writer.write("4.XXXXX\n");
                    writer.write("5.XXXXX\n");
                    writer.write("6.XXXXX");
                    writer.close();
                }
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] parsed = line.split("[.]");
                    if (parsed[1].equals("XXXXX")) {
                        System.out.printf("%s : 지점이 존재하지 않습니다.\n", parsed[0]);
                    } else if (parsed[1].equals("00000")) {
                        System.out.printf("%s : 스터디 공간을 생성해주세요.\n", parsed[0]);
                    } else {
                        System.out.printf("%s : ", parsed[0]);
                        for (int i = 0; i < parsed[1].length(); i++)
                            if (!parsed[1].substring(i,i+1).equals("X") && !parsed[1].substring(i,i+1).equals("0")) {
                                System.out.printf("%d호(%d인실) ", i+1, Integer.parseInt(parsed[1].substring(i,i+1), 16));
                            }

                        System.out.printf("\n");
                    }
                }
                reader.close();
            }
        }

    }
    private static class User{
        List<String> data_for_ID_file = new ArrayList<String>(); //ID file 만들 때 넣을 string
        List<String> data_for_date_file = new ArrayList<String>(); //data file 만들 때 넣을 string
        String user;

        public User() throws IOException { //로그인 과정
            System.out.println("---------------------------");
            System.out.println("- 사용자 모드로 로그인합니다. -");
            System.out.println("---------------------------");
            System.out.println("ID를 입력해주세요");
            String id = "";
            while (id.isEmpty()) {
                System.out.print("ID : ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String tmp = reader.readLine();
                if (tmp.equals("q")) break;
                if (checkID(tmp)){
                    id = tmp;
                    data_for_date_file.add(id);
                }else System.out.println("영문자 및 숫자 조합으로 최소 5글자 최대 10개 글자까지 허용됩니다.");
            }
            user = id;
            new Reservation();
            home();
        }

        boolean checkID(String ID){ //ID로 사용 가능하면 true, 불가능하면 false return
            boolean check = true;
            if (ID.length() < 5 || ID.length() > 10) return false;
            for (int i=0; i<ID.length(); i++){
                if (!Character.isDigit(ID.charAt(i)) && !Character.isLetter(ID.charAt(i))) check = false;
            }
            if(ID.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) check = false;
            if (check) return true;
            return false;
        }

        /*
         * 스터디 공간 예약 클래스.
         * 예약 성공 시 파일 두 개 생성 - 1)ID파일 2)날짜파일
         * 1번 파일은 사용자가 예약한 내용을 볼 수 있음
         * 2번 파일은 해당 날짜에 예약된 내용을 볼 수 있음
         */
        class Reservation{
            public Reservation() throws IOException {
                while (true) {
                    System.out.println("1)스터디 공간 조회\t\t2)신규 예약\t\t3)예약 수정");
                    System.out.println("(로그아웃하고 초기 화면으로 돌아가시려면 q를 입력해주세요)");

                    char mode = checkInput(1);
                    //원래는 사용자 편의를 위해 로그인 시 바로 신규예약을 할 수 있도록 지점 정보를 띄우려고 했으나,
                    //스터디 공간 조회 기능이 필수라서 넣지 않았습니다.
                    if (mode == '1') {
                        char branch = '0';
                        String[] range = new String[]{"1", "2", "3", "4", "5", "6"};
                        System.out.print("스터디 공간 조회를 위해 지점 정보를 불러옵니다.\n");
                        while (branch == '0') {
                            //System.out.println();
                            System.out.print("[공간조회]어느 지점 정보를 가져올까요? >> ");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                        /*if (str.charAt(0)!='1' && str.charAt(0)!='2' && str.charAt(0)!='3' && str.charAt(0)!='4' && str.charAt(0)!='5' && str.charAt(0)!='q'){

                        }*/
                            if (str.length() != 1) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break;
                                //작성을 종료하고 다시 mode를 입력받음.
                                //관리자/유저 모드를 하나의 로그인 형태로 인식하면, 홈 화면의 기준은 관리자/유저 모드임.
                                //또 보통의 예약 프로그램의 경우 입력 중단시 완전 초기화면으로 돌아가는 것이 아니라
                                //유저 기록을 유지한 상태에서 다른 기능을 사용할 수 있는 형태임.
                                //따라서 그러한 방식으로 알고리즘을 짰음.
                            }
                            if (!Arrays.asList(range).contains(str)) {
                                System.out.printf("해당 지점 번호는 사용할 수 없습니다. 1~6 사이의 자연수를 입력해주세요.\n", str);
                                continue;
                            }
                            if (!can_reservate_branch(str)) {
                                System.out.printf("※%s호점 스터디 공간은 존재하지 않습니다.\n", str);
                                continue;
                            }
                            branch = str.charAt(0);
                        }
                        System.out.printf("지점[%c번] 정보를 가져옵니다...\n", branch);
                        System.out.println("-----------------------------------------");
                        System.out.println("------------스터디 공간 상태 정보-----------");
                        Administrator.study_space_management.read(branch);
                    } else if (mode == '2') {
                        char branch = '0';
                        String[] range = new String[]{"1", "2", "3", "4", "5", "6"};
                        while (branch == '0') {
                            //System.out.println();
                            System.out.print("[신규예약]예약할 지점의 번호를 입력해주세요 >> ");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                            if (str.length() != 1) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }

                            if (!Arrays.asList(range).contains(str)) {
                                System.out.printf("지점[%s]은/는 존재하지 않습니다. 1~6 사이의 자연수를 입력해주세요.\n", str);
                                continue;
                            }
                            if (!can_reservate_branch(str)) {
                                System.out.printf("%s호점 스터디 공간은 존재하지 않습니다.\n", str);
                                continue;
                            }
                            branch = str.charAt(0);
                        }
                        String room = Administrator.study_space_management.read(branch);
                        String result = "";
                        for (int i=0; i<room.length(); i++) {
                            if (room.charAt(i) != '0') {
                                result += String.valueOf(i+1);
                                //result += String.valueOf(i);
                            }
                        }
                        char space = '0';
                        int people = 0;
                        while (space == '0' && people == 0){
                            System.out.print("[신규예약]예약할 방의 번호를 입력해주세요 >> ");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                            if (str.length() != 1) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            for (int i=0; i<result.length(); i++){
                                if (result.charAt(i)==str.charAt(0)){
                                    space = str.charAt(0);
                                    people = Integer.parseInt(String.valueOf(room.charAt(i)));
                                    break;
                                }
                            }
                            if (space == '0') System.out.printf("%s호실은 존재하지 않습니다.\n", str.charAt(0));
                        }
                        String date = "";
                        while (date.isBlank()){
                            System.out.printf("[신규예약]%s호실을 예약할 날짜를 6자리 숫자로 입력해주세요(ex:YYMMDD) >> ", space);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                            if (str.length() != 6) continue;
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            int year = Integer.parseInt(str.substring(0,2)) + 2000;
                            int month = Integer.parseInt(str.substring(2,4));
                            int day = Integer.parseInt(str.substring(4,6));
                            //연,월,일이 규칙에 맞는지 체크
                            if(checkDate(year,month,day)){
                                //연,월,일이 오늘 이후인지 체크
                                if (checkDatePassed(year, month, day)) {
                                    System.out.printf("※예약은 %d년 %d월 %d일부터 가능합니다.\n", year_today, month_today, day_today + 1);
                                }
                            }else{
                                System.out.printf("※%d년 %d월 %d일은 존재하지 않습니다.\n", year, month, day);
                            }
                        }
                        //8~22시까지의 예약 일정을 보여주고 예약 진행.
                        //이때 인원 입력은 방 제한 인원보다 낮은 숫자여야 함. 그보다 높은 숫자 입력 시 예약되지 않음.
                        String reservation_data = read_date(date, branch, space);
                        String impossible = "";
                        if (reservation_data != null){
                            System.out.println("-----------------------------------");
                            System.out.println("------------예약 가능 시간-----------");
                            for (int i=0;i<reservation_data.length();i++){
                                if (reservation_data.charAt(i) == '0'){
                                    System.out.printf("%3d ~ %3d : 예약 가능\n", i+8, i+9);
                                }
                                else {
                                    System.out.printf("%3d ~ %3d : 예약 불가\n", i+8, i+9);
                                    impossible += i+8;
                                }
                            }
                        }
                        //예약할 시간을 입력받음. 이때 예약 불가능한 시간을 예약 시도하는지 확인
                        int start = 0;
                        while (start == 0){
                            System.out.printf("[신규예약]%s호실 예약 시작 시간을 입력해주세요(8~21) >> ", space);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            try {
                                Integer.parseInt(str);
                            }catch (NumberFormatException e){
                                System.out.printf("8시부터 21시 사이의 숫자만 입력해주세요.\n");
                                continue;
                            }
                            if (Integer.parseInt(str) < 8 || Integer.parseInt(str) > 21){
                                System.out.printf("오전 8시부터 밤 10시까지만 예약이 가능합니다.\n");
                                continue;
                            }
                            if (impossible.contains(str)){
                                System.out.printf("해당 시간은 이미 예약된 시간입니다.\n");
                                continue;
                            }
                            start = Integer.parseInt(str);
                        }
                        int end = 0;
                        while (end == 0){
                            System.out.printf("[신규예약]%s호실 사용 예정시간을 입력해주세요(최소:1 최대:14) >> ", space);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String str = reader.readLine();
                            if (str.equals("q")) {
                                System.out.printf("입력을 중단합니다...\n");
                                break; //작성을 종료하고 다시 mode를 입력받음.
                            }
                            try {
                                Integer.parseInt(str);
                            }catch (NumberFormatException e){
                                System.out.printf("예약은 최소 1시간부터 최대 14시간까지만 가능합니다. 1~14 사이의 숫자만 입력해주세요.\n");
                                continue;
                            }
                            if (start + Integer.parseInt(str) > 22){
                                System.out.printf("%d시부터 예약 시 최대 %d시간까지만 사용 가능합니다.\n", start, 22-start);
                                continue;
                            }

                            boolean overlap = false;
                            for (int i=start; i<start+Integer.parseInt(str); i++) {
                                if (impossible.contains(String.valueOf(i))) {
                                    System.out.printf("해당 시간은 이미 예약된 시간입니다.\n");
                                    overlap = true;
                                    break;
                                }
                            }
                            if (overlap) continue;

                            end = start + Integer.parseInt(str);
                        }
                        add_date(date, branch, space, start, end);
                        add_user(date, user, branch, space, start, end);
                    } else if (mode == '3') {
                        //예약 수정 기능 + 조회 기능
                        //해당 ID 파일 읽어서 예약 정보 한 줄씩 배열에 저장.
                        //한 줄씩 번호 붙여서 print해서 보여주고, 수정을 원하는 번호를 입력하면 수정모드로 들어감.
                        //삭제 후 다시 예약하는 방식으로 수정이 진행됨.
                        String filePath = "src/"+user+".txt";
                        File file = new File(filePath); // File 객체 생성
                        if (!file.exists()) {           // 파일이 존재하지 않으면
                            System.out.printf("예약한 내용이 없습니다.\n");
                        }else {
                            System.out.printf("예약 내역을 불러옵니다.\n");
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line;
                            String document = "";
                            int i=1;
                            while ((line = reader.readLine()) != null) {
                                String[] parsed = line.split("[.]");
                                String date_data = parsed[0];
                                int year = Integer.parseInt(date_data.substring(0,2));
                                int month = Integer.parseInt(date_data.substring(2,4));
                                int day = Integer.parseInt(date_data.substring(4,6));
                                if (!checkDatePassed(year, month, day)) { //미래의 예약만 출력
                                    String branch_data = parsed[1].substring(0, 1);
                                    String space_data = parsed[1].substring(2, 3);
                                    String reservation_data = parsed[2];

                                    System.out.printf("%d.\n");
                                    System.out.printf("예약일자 : %d년 %d월 %d일\n", year, month, day);
                                    System.out.printf("예약장소 : 지점[%s] %s호\n", branch_data, space_data);
                                    System.out.printf("예약시간 : %c시~%c시\n", reservation_data.indexOf("1") + 8, reservation_data.lastIndexOf("1") + 8);

                                    document += i+"."+line+"/";
                                    i++;
                                }
                            }
                            int num = 0;
                            while (num == 0) {
                                //System.out.println();
                                System.out.print("[예약수정]수정하고자 하는 예약 넘버를 입력해주세요 >> ");
                                reader = new BufferedReader(new InputStreamReader(System.in));
                                String str = reader.readLine();
                                try {
                                    Integer.parseInt(str);
                                }catch (NumberFormatException e){
                                    System.out.println("숫자를 입력해주세요.");
                                    continue;
                                }
                                if (Integer.parseInt(str) < i && Integer.parseInt(str) > 0){
                                    String[] target = document.split("[/]"); //수정 대상 : target[i-1]

                                }
                            }
                        }
                    } else if (mode == 'q') break;
                }
                System.out.printf("초기 화면으로 돌아갑니다..\n");
            }
        }

        private boolean checkDatePassed(int year, int month, int day) {
            //날이 지났는지 확인. 현재 날짜를 기준으로 과거면 true, 미래면 false return.
            if (year_today < year) return false;
            else if (year_today == year && month_today < month) return false;
            else if (year_today == year && month_today == month && day_today < day) return false;
            return true;
        }

        static String read_date(String date, char branch, char space) throws IOException {
            //해당 날짜 파일로 가서 해당 지점의 방에 예약 잡혀있는지 확인.
            //예약 형식 : N-N.00000000000000
            //N호점 N호실, 0은 8시부터 10시까지 1시간 간격으로 센다. 예약이 잡힐 경우 해당 부분 1로 바뀐다.
            //만약 날짜 파일이 존재하지 않는다면 아무 예약도 잡혀있지 않은 것이므로 그냥 해당 호실 예약 생성만 해주면 됨.
            String filePath = "src/"+date+".txt";
            File file = new File(filePath); // File 객체 생성
            if (!file.exists()) {           // 파일이 존재하지 않으면
                file.createNewFile();       // 신규생성
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                writer.write(branch+"-"+space+".");
                for (int i=8; i<22; i++){
                    writer.write("0");
                }
                writer.write("\n");
                writer.close();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                String room_data = parsed[0];
                String reservation_data = parsed[1];
                if (room_data.equals(branch+"-"+space)){
                    return reservation_data; //예약하고자 하는 방의 예약정보 리턴
                }
            }
            //현재 예약하고자 하는 방에 대한 정보가 없다면 -> 생성
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(branch+"-"+space+".");
            for (int i=8; i<22; i++){
                writer.write("0");
            }
            writer.write("\n");
            writer.close();

            //다시 읽기 진행
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                String room_data = parsed[0];
                String reservation_data = parsed[1];
                if (room_data.equals(branch+"-"+space)){
                    return reservation_data; //예약하고자 하는 방의 예약정보 리턴
                }
            }
            reader.close();
            return null; //일어나서는 안 되는 리턴값.
        }

        void add_date(String date, char branch, char space, int start, int end) throws IOException {
            //날짜 파일 수정
            String filePath = "src/"+date+".txt";
            String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
            //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

            //Files.delete(Paths.get(filePath));
            StringBuffer stringBuffer = new StringBuffer(contents);
            int index = contents.indexOf(branch+"-"+space+".")+4+(start-8);
            String to_replace = "";
            for (int i=0; i<end-start; i++){
                to_replace += '1';
            }
            stringBuffer.replace(index,index+(end-start), to_replace); //str 크기가 길이 개수만큼이어야하나?
            contents = stringBuffer.toString();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.write(contents);
            writer.close();

            System.out.printf("스터디 공간 예약이 완료되었습니다.\n");
        }

        void delete_date(String date, char branch, char space, int start, int end) throws IOException {
            //날짜 파일 수정
            String filePath = "src/"+date+".txt";
            String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
            //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

            //Files.delete(Paths.get(filePath));
            StringBuffer stringBuffer = new StringBuffer(contents);
            int index = contents.indexOf(branch+"-"+space+".")+4+(start-8);
            String to_replace = "";
            for (int i=0; i<end-start; i++){
                to_replace += '0';
            }
            stringBuffer.replace(index,index+(end-start), to_replace); //str 크기가 길이 개수만큼이어야하나?
            contents = stringBuffer.toString();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.write(contents);
            writer.close();

            System.out.printf("스터디 공간 예약이 취소되었습니다.\n");
        }

        static String read_user(String date, String id ,char branch, char space) throws IOException {
            //해당 유저 파일로 가서 예약 정보 확인.
            //예약 형식 : 20220614.N-N.00000000000000
            //형식은 read_date와 유사하다.
            String filePath = "src/"+id+".txt";
            File file = new File(filePath); // File 객체 생성
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                String date_data = parsed[0];
                String room_data = parsed[1];
                String reservation_data = parsed[2];
                if (room_data.equals(branch+"-"+space) && date_data.equals(date)){
                    return reservation_data; //예약하고자 하는 방의 예약정보 리턴
                }
            }
            //현재 예약하고자 하는 방에 대한 정보가 없다면 -> 생성
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(date+"."+branch+"-"+space+".");
            for (int i=8; i<22; i++){
                writer.write("0");
            }
            writer.write("\n");
            writer.close();

            //다시 읽기 진행
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                String date_data = parsed[0];
                String room_data = parsed[1];
                String reservation_data = parsed[2];
                if (room_data.equals(branch+"-"+space) && date_data.equals(date)){
                    return reservation_data; //예약하고자 하는 방의 예약정보 리턴
                }
            }
            reader.close();
            return null; //일어나서는 안 되는 리턴값.
        }

        void add_user(String date, String id, char branch, char space, int start, int end) throws IOException {
            String filePath = "src/" + id + ".txt";
            File file = new File(filePath); // File 객체 생성
            if (!file.exists()) {           // 파일이 존재하지 않으면
                file.createNewFile();       // 신규생성
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(date + "." + branch + "-" + space + ".");
            for (int i = 8; i < 22; i++) {
                if (i>=start && i<=end) writer.write("1");
                else writer.write("0");
            }
            writer.write("\n");
            writer.close();

            System.out.printf("스터디 공간 예약이 완료되었습니다.\n");
        }
        
        void del_user(String date, String id, char branch, char space, int start, int end) throws IOException {
            //유저 파일 수정
            String filePath = "src/"+id+".txt";
            String contents = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset()); // contents 는 전체 파일 내용
            //String contents = String.valueOf(Files.readAllLines(Paths.get(filePath))); // contents 는 전체 파일 내용

            //Files.delete(Paths.get(filePath));
            StringBuffer stringBuffer = new StringBuffer(contents);
            int index = contents.indexOf(date+"."+branch+"-"+space+".")+4+(start-8);
            stringBuffer.replace(index,index+19, ""); //엔터도 지워지나?
            contents = stringBuffer.toString();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.write(contents);
            writer.close();

            System.out.printf("스터디 공간 예약이 취소되었습니다.\n");
        }

        private boolean can_reservate_branch(String str) throws IOException { //예약할 수 있으면 true, 예약할 수 없으면 false return
            String filePath = "src/Administrator_info.txt";
            File file = new File(filePath);
            BufferedReader file_reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = file_reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                if (parsed[0].equals(str)) {
                    if (parsed[1].equals("00000")) return false;
                    else if (parsed[1].equals("XXXXX")) return false;
                }
            }
            return true;
        }


        private boolean can_reservate_space(String str) throws IOException { //예약할 수 있으면 true, 예약할 수 없으면 false return
            String filePath = "src/Administrator_info.txt";
            File file = new File(filePath);
            BufferedReader file_reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = file_reader.readLine()) != null) {
                String[] parsed = line.split("[.]");
                if (parsed[0].equals(str)) {
                    if (parsed[1].equals("00000")) return false;
                    else if (parsed[1].equals("XXXXX")) return false;
                }
            }
            return true;
        }

        public static boolean checkDate(int year, int month, int day) {
            if (year < 0) return false;
            if (month > 12 || month < 0 ) return false;
            if (day > 31 || day < 0) return false;
            if (month == 4 || month == 6 || month == 9 || month == 11){
                if (day > 30) return false;
            }
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){ //윤달일때
                if (month == 2 && day > 29) return false;
            }else { //윤달 아닐떄
                if (month == 2 && day > 28) return false;
            }
            return true;
        }
    }
    private static char checkInput(int scenario) throws IOException { //숫자 1, 2, 3 중 하나를 입력받았는지 확인
        char mode = '0';
        while (mode!='1' && mode!='2' && mode!='3' && mode!='q'){
            //System.out.println();
            switch (scenario){
                case 0:
                    System.out.print("숫자를 입력해주세요 >> ");
                    break;
                case 1:
                    System.out.print("사용하실 기능에 해당하는 숫자를 입력해주세요 >> ");
                    break;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = reader.readLine();
            if (str.length()!=1) continue;
            mode = str.charAt(0);
        }
        return mode;
    }
    private static class Exit{
        public Exit(){
            System.out.println("-----프로그램을 종료합니다-----");
            System.out.println("---------------------------");
            System.exit(0);
        }
    }
    private static void q() throws IOException {
        System.out.printf("과정을 중단하고 홈 화면으로 돌아갑니다...\n");
        home();
    }

}
