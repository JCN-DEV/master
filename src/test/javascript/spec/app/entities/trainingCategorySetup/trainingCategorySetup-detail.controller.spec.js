'use strict';

describe('TrainingCategorySetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTrainingCategorySetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTrainingCategorySetup = jasmine.createSpy('MockTrainingCategorySetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TrainingCategorySetup': MockTrainingCategorySetup
        };
        createController = function() {
            $injector.get('$controller')("TrainingCategorySetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingCategorySetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
