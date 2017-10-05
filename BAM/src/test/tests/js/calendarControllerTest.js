describe('calendarController', function()
{
    beforeEach(module('bam')); //load the app

    //mock data for the SessionService
    var mockSessionCurrent = {};

    var mockSessionTrainer =
        {
            currentUser:
                {
                    role: 2
                }
        };

    var mockSessionAssociateWithoutBatch =
        {
            currentUser:
                {
                    role: 1
                }
        };

    var mockSessionAssociateWithBatch =
        {
            currentUser:
                {
                    role: 1,

                    batch:
                        {
                            id: 1
                        }
                }
        };

    //mock data for the subtopic service
    var testSubtopics =
        [
            {},
            {},
            {}
        ];

    //stub methods for the mocked services
    beforeEach(angular.mock.module({
        'SessionService' :
            {
                set : function(key, value) {
                    eval("mockSessionCurrent." + key + " = " + value);
                },
                get : function(key) {
                    return eval("mockSessionCurrent." + key);
                }
            },

        'SubtopicService' :
            {
                //the normal function returns a promise, so we have to fake a promise
                getTotalNumberOfSubtopics : function(batchId)
                {
                    return {then: function()
                        { return testSubtopics.length; }
                    }
                }
            }
    }));

    //get the service responsible for instantiating controllers
    var $controller;

    beforeEach(inject(function(_$controller_)
    {
        $controller = _$controller_;
    }));

    //get the location service
    var $location;
    beforeEach(inject(function(_$location_)
    {
        $location = _$location_;
    }));

    //variables used in multiple tests
    var $scope, $rootScope, controller;

    //perform tests
    describe("test:", function()
    {
        //declare these for every test
        beforeEach(function()
        {
            //reset these objects
            $scope = {};
            $rootScope = {};
        });

        //helper to instantiate in the same manner for every test
        var instantiateController = function()
        {
            controller = $controller('calendarController', {$scope:$scope, $rootScope:$rootScope});
        };

        describe("trainer", function()
        {
            //do this for every trainer test
            beforeEach(function()
            {
                mockSessionCurrent = mockSessionTrainer;
            });

            it ("should not be redirected", function()
            {
                //immediately fail if location.path is called
                spyOn($location, 'path').and.throwError("fail: user was redirected");

                //instantiate the controller with the above objects
                instantiateController();
            });
        });

        describe("associate", function()
        {
            //do this for every associate test
            beforeEach(function()
            {
                mockSessionCurrent = mockSessionAssociateWithBatch;
            });

            it ("without batch should be redirected to '/noBatch'", function()
            {
                mockSessionCurrent = mockSessionAssociateWithoutBatch; //run as associate without a batch

                //raise a flag when this gets called
                var redirected = false;
                spyOn($location, 'path').and.callFake(function(){redirected = true;});

                //ignore exceptions if the user was redirected
                try
                {
                    instantiateController();
                }
                catch (err)
                {
                    if (!redirected)
                        throw err;
                }

                expect(redirected);
            });

            it ("should not be redirected", function()
            {
                //immediately fail if location.path is called
                spyOn($location, 'path').and.throwError("fail: user was redirected");

                //instantiate the controller with the above objects
                instantiateController();
            });
        });
    });
});