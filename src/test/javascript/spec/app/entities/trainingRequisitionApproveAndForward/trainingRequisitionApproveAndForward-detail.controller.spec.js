'use strict';

describe('TrainingRequisitionApproveAndForward Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingRequisitionApproveAndForward, MockTrainingRequisitionForm;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingRequisitionApproveAndForward = jasmine.createSpy('MockTrainingRequisitionApproveAndForward');
        MockTrainingRequisitionForm = jasmine.createSpy('MockTrainingRequisitionForm');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingRequisitionApproveAndForward': MockTrainingRequisitionApproveAndForward,
            'TrainingRequisitionForm': MockTrainingRequisitionForm
        };
        createController = function() {
            $injector.get('$controller')("TrainingRequisitionApproveAndForwardDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingRequisitionApproveAndForwardUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
