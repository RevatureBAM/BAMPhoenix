package com.bam.service.unit;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.repository.BamUserRepository;
import com.bam.repository.BatchRepository;
import com.bam.service.BamUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class BamUserServiceTest
{
    @Mock
    private BamUserRepository userRepository;

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private BamUserService userService;

    //test data
    private BamUser testTrainer;
    private BatchType testBatchType;
    private Batch testBatch;
    private BamUser testAssociate1;
    private BamUser testAssociate2;
    private BamUser testAssociate3;
    private List<BamUser> testUsersInBatch;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        //trainer
        //associates
        testTrainer = new BamUser(
                1,                                         //userId
                "August", null, "Duet",      //names
                "AugustDuet@fakemail.com", "password", //login info
                2,                                           //role
                null,                                       //current batch (associates only!)
                "555-5555-5555", null, null,   //contact info
                null,                                       //for password resets
                1                                     //ID of the same user in assignForce
        );

        //batch type
        testBatchType = new BatchType(1, "TEST", 10);

        //batch
        testBatch = new Batch
                (
                        1,                                                                          //ID
                        "Test",                                                                  //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        testTrainer,                                                                   //trainer for the batch
                        testBatchType                                                                  //batch type
                );

        //associates
        testAssociate1 = new BamUser
                (
                        2,                                      //userId
                        "John", null, "Doe",      //names
                        "JohnDoe@fakemail.com", "password",  //login info
                        1,                                         //role
                        testBatch,                                     //current batch (associates only!)
                        "555-5555-5556", null, null, //contact info
                        null,                                     //for password resets
                        2                                   //ID of the same user in assignForce
                );

        testAssociate2 = new BamUser
                (
                        3,                                      //userId
                        "Jane", null, "Doe",      //names
                        "JaneDoe@fakemail.com", "password",  //login info
                        1,                                         //role
                        testBatch,                                     //current batch (associates only!)
                        "555-5555-5557", "555-5555-5558", null, //contact info
                        null,                                     //for password resets
                        3                                   //ID of the same user in assignForce
                );

        testAssociate3 = new BamUser
                (
                        4,                                      //userId
                        "Some", "Fake", "Name",    //names
                        "SomeName@someMail.ws", "pa$$w0rd",  //login info
                        1,                                         //role
                        null,                                     //current batch (associates only!)
                        "555-5555-5559", null, null, //contact info
                        null,                                     //for password resets
                        4                                   //ID of the same user in assignForce
                );

        testUsersInBatch = new ArrayList<>();
        testUsersInBatch.add(testAssociate1);
        testUsersInBatch.add(testAssociate2);

        when(batchRepository.findById(testBatch.getId())).thenReturn(testBatch);
        when(userRepository.findByBatch(testBatch)).thenReturn(testUsersInBatch);
    }

    @Test
    public void findUsersInBatch() throws Exception
    {
        List<BamUser> result = userService.findUsersInBatch(testBatch.getId());

        assertNotNull(result); //result must not be null

        //all returned users must be associates
        for (BamUser u : result)
            if (u.getRole() != 1)
                fail();

        //given the data above, there should be two returned
        assert(result.size() == 2);
    }

    @Test
    public void findUsersNotInBatch() throws Exception
    {
        List<BamUser> result = userService.findUsersNotInBatch();

        assertNotNull(result); //result must not be null

        //all returned users must be associates
        for (BamUser u : result)
            if (u.getRole() != 1)
                fail();

        //given the data above, there should be one returned
        assert(result.size() == 1);
    }

    

}