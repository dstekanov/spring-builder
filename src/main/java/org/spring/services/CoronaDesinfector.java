package org.spring.services;

public class CoronaDesinfector {

    // 3 Ответсвенности: выбрать реализацыю, создать обьект, настроить
//    private Announcer announcer = new ConsoleAnnouncer();

    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

    public void start(Room room) {
        // этот енаунсер тут, для лучшего переиспользования.
        // Если завтра появится холодильник, которому тоже надо что-то сообщать, то он спокойно может переиспользовать этот еннаунсер
        announcer.announce("надо выйти из помещения!");
        policeman.getOutPeople();
        desinfect(room);
        announcer.announce("рискните вернутся, вроде короны не видно");
    }

    private void desinfect(Room room) {
        System.out.println("зачитывается молитва: 'корона изыди!' - молитва прочитана, вирус низвергается в ад");
    }
}
