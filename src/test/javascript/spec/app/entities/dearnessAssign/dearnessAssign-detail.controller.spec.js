'use strict';

describe('DearnessAssign Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDearnessAssign, MockPayScale;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDearnessAssign = jasmine.createSpy('MockDearnessAssign');
        MockPayScale = jasmine.createSpy('MockPayScale');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DearnessAssign': MockDearnessAssign,
            'PayScale': MockPayScale
        };
        createController = function() {
            $injector.get('$controller')("DearnessAssignDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dearnessAssignUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
