'use strict';

describe('TrainerEvaluationInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainerEvaluationInfo, MockTrainingHeadSetup, MockTrainingInitializationInfo, MockTraineeInformation;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainerEvaluationInfo = jasmine.createSpy('MockTrainerEvaluationInfo');
        MockTrainingHeadSetup = jasmine.createSpy('MockTrainingHeadSetup');
        MockTrainingInitializationInfo = jasmine.createSpy('MockTrainingInitializationInfo');
        MockTraineeInformation = jasmine.createSpy('MockTraineeInformation');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainerEvaluationInfo': MockTrainerEvaluationInfo,
            'TrainingHeadSetup': MockTrainingHeadSetup,
            'TrainingInitializationInfo': MockTrainingInitializationInfo,
            'TraineeInformation': MockTraineeInformation
        };
        createController = function() {
            $injector.get('$controller')("TrainerEvaluationInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainerEvaluationInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
