'use strict';

describe('Training Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTraining, MockDistrict, MockEmployee, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTraining = jasmine.createSpy('MockTraining');
        MockDistrict = jasmine.createSpy('MockDistrict');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Training': MockTraining,
            'District': MockDistrict,
            'Employee': MockEmployee,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("TrainingDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:trainingUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
