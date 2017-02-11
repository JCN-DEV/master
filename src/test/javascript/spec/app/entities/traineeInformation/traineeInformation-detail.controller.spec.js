'use strict';

describe('TraineeInformation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTraineeInformation, MockTrainingHeadSetup, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTraineeInformation = jasmine.createSpy('MockTraineeInformation');
        MockTrainingHeadSetup = jasmine.createSpy('MockTrainingHeadSetup');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TraineeInformation': MockTraineeInformation,
            'TrainingHeadSetup': MockTrainingHeadSetup,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function() {
            $injector.get('$controller')("TraineeInformationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:traineeInformationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
