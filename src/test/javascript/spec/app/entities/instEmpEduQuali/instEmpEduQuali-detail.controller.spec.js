'use strict';

describe('InstEmpEduQuali Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmpEduQuali, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmpEduQuali = jasmine.createSpy('MockInstEmpEduQuali');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmpEduQuali': MockInstEmpEduQuali,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("InstEmpEduQualiDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmpEduQualiUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
