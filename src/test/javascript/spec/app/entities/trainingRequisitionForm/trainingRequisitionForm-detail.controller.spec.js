'use strict';

describe('TrainingRequisitionForm Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingRequisitionForm, MockTrainingCategorySetup, MockTrainingHeadSetup, MockCountry, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingRequisitionForm = jasmine.createSpy('MockTrainingRequisitionForm');
        MockTrainingCategorySetup = jasmine.createSpy('MockTrainingCategorySetup');
        MockTrainingHeadSetup = jasmine.createSpy('MockTrainingHeadSetup');
        MockCountry = jasmine.createSpy('MockCountry');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingRequisitionForm': MockTrainingRequisitionForm,
            'TrainingCategorySetup': MockTrainingCategorySetup,
            'TrainingHeadSetup': MockTrainingHeadSetup,
            'Country': MockCountry,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function() {
            $injector.get('$controller')("TrainingRequisitionFormDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingRequisitionFormUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
