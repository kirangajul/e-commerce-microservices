package com.kirangajul.inventoryservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kirangajul.inventoryservice.model.Inventory;
import com.kirangajul.inventoryservice.repository.InventoryRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (inventoryRepository.count() == 0) {
            Inventory smartphone = new Inventory();
            smartphone.setProductName("Smartphone");
            smartphone.setQuantity(50);

            Inventory laptop = new Inventory();
            laptop.setProductName("Laptop");
            laptop.setQuantity(30);

            Inventory fictionBook = new Inventory();
            fictionBook.setProductName("Fiction Book");
            fictionBook.setQuantity(100);

            Inventory nonFictionBook = new Inventory();
            nonFictionBook.setProductName("Non-Fiction Book");
            nonFictionBook.setQuantity(80);

            Inventory mensTshirt = new Inventory();
            mensTshirt.setProductName("Men's T-Shirt");
            mensTshirt.setQuantity(120);

            Inventory womensDress = new Inventory();
            womensDress.setProductName("Women's Dress");
            womensDress.setQuantity(60);

            Inventory refrigerator = new Inventory();
            refrigerator.setProductName("Refrigerator");
            refrigerator.setQuantity(15);

            Inventory microwave = new Inventory();
            microwave.setProductName("Microwave Oven");
            microwave.setQuantity(40);

            Inventory toyCar = new Inventory();
            toyCar.setProductName("Toy Car");
            toyCar.setQuantity(200);

            Inventory actionFigure = new Inventory();
            actionFigure.setProductName("Action Figure");
            actionFigure.setQuantity(150);

            Inventory basketball = new Inventory();
            basketball.setProductName("Basketball");
            basketball.setQuantity(100);

            Inventory tennisRacket = new Inventory();
            tennisRacket.setProductName("Tennis Racket");
            tennisRacket.setQuantity(70);

            Inventory shampoo = new Inventory();
            shampoo.setProductName("Shampoo");
            shampoo.setQuantity(300);

            Inventory lipstick = new Inventory();
            lipstick.setProductName("Lipstick");
            lipstick.setQuantity(200);

            Inventory carTires = new Inventory();
            carTires.setProductName("Car Tires");
            carTires.setQuantity(40);

            Inventory carBattery = new Inventory();
            carBattery.setProductName("Car Battery");
            carBattery.setQuantity(30);

            Inventory gardenHose = new Inventory();
            gardenHose.setProductName("Garden Hose");
            gardenHose.setQuantity(100);

            Inventory lawnMower = new Inventory();
            lawnMower.setProductName("Lawn Mower");
            lawnMower.setQuantity(20);

            Inventory sofa = new Inventory();
            sofa.setProductName("Sofa");
            sofa.setQuantity(10);

            Inventory diningTable = new Inventory();
            diningTable.setProductName("Dining Table");
            diningTable.setQuantity(15);

            Inventory smartwatch = new Inventory();
            smartwatch.setProductName("Smartwatch");
            smartwatch.setQuantity(80);

            Inventory ebookReader = new Inventory();
            ebookReader.setProductName("E-Book Reader");
            ebookReader.setQuantity(60);

            Inventory mensShoes = new Inventory();
            mensShoes.setProductName("Men's Shoes");
            mensShoes.setQuantity(90);

            Inventory womensHandbag = new Inventory();
            womensHandbag.setProductName("Women's Handbag");
            womensHandbag.setQuantity(50);

            Inventory washingMachine = new Inventory();
            washingMachine.setProductName("Washing Machine");
            washingMachine.setQuantity(25);

            inventoryRepository.save(smartphone);
            inventoryRepository.save(laptop);
            inventoryRepository.save(fictionBook);
            inventoryRepository.save(nonFictionBook);
            inventoryRepository.save(mensTshirt);
            inventoryRepository.save(womensDress);
            inventoryRepository.save(refrigerator);
            inventoryRepository.save(microwave);
            inventoryRepository.save(toyCar);
            inventoryRepository.save(actionFigure);
            inventoryRepository.save(basketball);
            inventoryRepository.save(tennisRacket);
            inventoryRepository.save(shampoo);
            inventoryRepository.save(lipstick);
            inventoryRepository.save(carTires);
            inventoryRepository.save(carBattery);
            inventoryRepository.save(gardenHose);
            inventoryRepository.save(lawnMower);
            inventoryRepository.save(sofa);
            inventoryRepository.save(diningTable);
            inventoryRepository.save(smartwatch);
            inventoryRepository.save(ebookReader);
            inventoryRepository.save(mensShoes);
            inventoryRepository.save(womensHandbag);
            inventoryRepository.save(washingMachine);
        }
    }
}
