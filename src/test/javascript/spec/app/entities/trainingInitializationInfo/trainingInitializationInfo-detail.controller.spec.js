'use strict';

describe('TrainingInitializationInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingInitializationInfo, MockTrainingHeadSetup, MockTrainingRequisitionForm;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingInitializationInfo = jasmine.createSpy('MockTrainingInitializationInfo');
        MockTrainingHeadSetup = jasmine.createSpy('MockTrainingHeadSetup');
        MockTrainingRequisitionForm = jasmine.createSpy('MockTrainingRequisitionForm');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingInitializationInfo': MockTrainingInitializationInfo,
            'TrainingHeadSetup': MockTrainingHeadSetup,
            'TrainingRequisitionForm': MockTrainingRequisitionForm
        };
        createController = function() {
            $injector.get('$controller')("TrainingInitializationInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingInitializationInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
