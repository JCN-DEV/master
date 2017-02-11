'use strict';

describe('TrainingHeadSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingHeadSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingHeadSetup = jasmine.createSpy('MockTrainingHeadSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingHeadSetup': MockTrainingHeadSetup
        };
        createController = function() {
            $injector.get('$controller')("TrainingHeadSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingHeadSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
