'use strict';

describe('JpEmployeeTraining Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpEmployeeTraining, MockJpEmployee, MockCountry;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpEmployeeTraining = jasmine.createSpy('MockJpEmployeeTraining');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        MockCountry = jasmine.createSpy('MockCountry');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpEmployeeTraining': MockJpEmployeeTraining,
            'JpEmployee': MockJpEmployee,
            'Country': MockCountry
        };
        createController = function() {
            $injector.get('$controller')("JpEmployeeTrainingDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpEmployeeTrainingUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
