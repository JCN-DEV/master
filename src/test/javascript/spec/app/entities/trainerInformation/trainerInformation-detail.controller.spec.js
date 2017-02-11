'use strict';

describe('TrainerInformation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainerInformation, MockTrainingInitializationInfo, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainerInformation = jasmine.createSpy('MockTrainerInformation');
        MockTrainingInitializationInfo = jasmine.createSpy('MockTrainingInitializationInfo');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainerInformation': MockTrainerInformation,
            'TrainingInitializationInfo': MockTrainingInitializationInfo,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function() {
            $injector.get('$controller')("TrainerInformationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainerInformationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
