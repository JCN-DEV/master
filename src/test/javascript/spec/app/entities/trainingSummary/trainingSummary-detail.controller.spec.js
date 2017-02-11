'use strict';

describe('TrainingSummary Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingSummary, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingSummary = jasmine.createSpy('MockTrainingSummary');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingSummary': MockTrainingSummary,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("TrainingSummaryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingSummaryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
