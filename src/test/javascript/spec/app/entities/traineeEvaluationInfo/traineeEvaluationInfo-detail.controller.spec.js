'use strict';

describe('TraineeEvaluationInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTraineeEvaluationInfo, MockTrainingHeadSetup, MockHrEmployeeInfo, MockTrainingInitializationInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTraineeEvaluationInfo = jasmine.createSpy('MockTraineeEvaluationInfo');
        MockTrainingHeadSetup = jasmine.createSpy('MockTrainingHeadSetup');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        MockTrainingInitializationInfo = jasmine.createSpy('MockTrainingInitializationInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TraineeEvaluationInfo': MockTraineeEvaluationInfo,
            'TrainingHeadSetup': MockTrainingHeadSetup,
            'HrEmployeeInfo': MockHrEmployeeInfo,
            'TrainingInitializationInfo': MockTrainingInitializationInfo
        };
        createController = function() {
            $injector.get('$controller')("TraineeEvaluationInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:traineeEvaluationInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
