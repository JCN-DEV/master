'use strict';

describe('TrainingBudgetInformation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingBudgetInformation, MockTrainingInitializationInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingBudgetInformation = jasmine.createSpy('MockTrainingBudgetInformation');
        MockTrainingInitializationInfo = jasmine.createSpy('MockTrainingInitializationInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingBudgetInformation': MockTrainingBudgetInformation,
            'TrainingInitializationInfo': MockTrainingInitializationInfo
        };
        createController = function() {
            $injector.get('$controller')("TrainingBudgetInformationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingBudgetInformationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
