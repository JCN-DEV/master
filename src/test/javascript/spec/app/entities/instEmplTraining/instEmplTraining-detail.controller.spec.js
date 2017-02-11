'use strict';

describe('InstEmplTraining Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmplTraining, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmplTraining = jasmine.createSpy('MockInstEmplTraining');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmplTraining': MockInstEmplTraining,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("InstEmplTrainingDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmplTrainingUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
